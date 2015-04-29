package it.sia.stresstest.beans;

import it.sia.stresstest.StressTestApplication;

import java.util.Arrays;
import java.util.List;

import javax.xml.ws.BindingProvider;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tonbeller.sironkyc.ws.data.xsd.BoData;
import com.tonbeller.sironkyc.ws.data.xsd.BusinessRuleResult;
import com.tonbeller.sironkyc.ws.data.xsd.CustomField;
import com.tonbeller.sironkyc.ws.data.xsd.DetailListMatch;
import com.tonbeller.sironkyc.ws.data.xsd.DetailRequestResult01;
import com.tonbeller.sironkyc.ws.data.xsd.DetailResultData01;
import com.tonbeller.sironkyc.ws.data.xsd.KycActions;
import com.tonbeller.sironkyc.ws.data.xsd.KycRisk;
import com.tonbeller.sironkyc.ws.data.xsd.LoginData;
import com.tonbeller.sironkyc.ws.data.xsd.PersonData;
import com.tonbeller.sironkyc.ws.data.xsd.RequestResult01;
import com.tonbeller.sironkyc.ws.data.xsd.ResultData01;
import com.tonbeller.sironkyc.ws.server.KYCService01;
import com.tonbeller.sironkyc.ws.server.KYCService01PortType;

public class TestKYCScoring implements TestWebService {

  private final static Logger log = LoggerFactory.getLogger(TestKYCScoring.class);

  public String start() {
    try {
      KYCService01 service = new KYCService01();
      KYCService01PortType port = service.getKYCService01HttpSoap11Endpoint();
      BindingProvider provider = (BindingProvider) port;
      provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
          StressTestApplication.props.getProperty("kyc.url"));

      final PersonData[] persons = new PersonData[1];
      persons[0] = new PersonData();
      persons[0].setPersonID(StressTestApplication.props.getProperty("kyc.scoring.person.id"));
      persons[0].setLastName(StressTestApplication.props.getProperty("kyc.scoring.person.lastname"));
      persons[0].setFirstName(StressTestApplication.props.getProperty("kyc.scoring.person.firstname"));
      persons[0].setBirthCountry(StressTestApplication.props.getProperty("kyc.scoring.person.birthcountry"));

      final CustomField[] customFields = new CustomField[3];
      customFields[0] = new CustomField();
      customFields[0].setKey(StressTestApplication.props.getProperty("kyc.scoring.custom.0.key"));
      customFields[0].setValue(StressTestApplication.props.getProperty("kyc.scoring.custom.0.value"));
      customFields[1] = new CustomField();
      customFields[1].setKey(StressTestApplication.props.getProperty("kyc.scoring.custom.1.key"));
      customFields[1].setValue(StressTestApplication.props.getProperty("kyc.scoring.custom.1.value"));
      customFields[2] = new CustomField();
      customFields[2].setKey(StressTestApplication.props.getProperty("kyc.scoring.custom.2.key"));
      customFields[2].setValue(StressTestApplication.props.getProperty("kyc.scoring.custom.2.value"));

      final BoData[] benOwners = new BoData[1];
      benOwners[0] = new BoData();
      benOwners[0].setBoKundnr(StressTestApplication.props.getProperty("kyc.scoring.bodata.bokundr"));
      // NDG del soggetto contenuto nell'oggetto persons a cui si deve associare questo BO
      benOwners[0].setKundnr(StressTestApplication.props.getProperty("kyc.scoring.bodata.kundnr"));
      // Tipo di relazione definibili nel front end
      benOwners[0].setRelType(StressTestApplication.props.getProperty("kyc.scoring.bodata.reltype"));

      LoginData login = new LoginData();
      login.setPass(StressTestApplication.props.getProperty("kyc.scoring.pwd"));
      login.setUserName(StressTestApplication.props.getProperty("kyc.scoring.user"));

      // Scoring
      RequestResult01 reqResult = port.kycScore(login, Arrays.asList(persons), Arrays.asList(benOwners));

      int returnCode = reqResult.getReturnCode();

      if (returnCode != 0) {
        log.info(reqResult.getExceptionText());
        System.exit(returnCode);
      }

      List<ResultData01> results = reqResult.getResultData();

      String riskPerc = "";
      String riskDesc = "";

      for (ResultData01 result : results) {
        List<KycActions> actions = result.getActions(); // azioni da intraprendere in base al rischio calcolato
        if (actions != null)
          for (KycActions action : actions)
            riskPerc = "" + action.getRang() + " - " + action.getLongText(); // rischio in forma numerica e descrittiva

        List<KycRisk> risks = result.getRisks();
        if (risks != null)
          for (KycRisk risk : risks)
            riskDesc = risk.getRiskDesc(); // rischio finale in forma descrittiva

        log.info(String.format("Rischio per #%s: %s (%s%%)", result.getClientId(), riskDesc, riskPerc));
      }
      // fine scoring

      // recupero del dettaglio del calcolo del rischio
      List<String> clientIds = Arrays.asList(new String[] { results.get(0).getClientId() });
      DetailRequestResult01 drr = port.kycGetDetailResults(login, clientIds);
      List<DetailResultData01> drds = drr.getResultData();

      boolean brMatch = false;
      String brMatched = "";

      for (DetailResultData01 drd : drds) {
        if (drd == null)
          break; // il soggetto non è mai stato controllato

        List<BusinessRuleResult> brrs = drd.getBrMatches();
        if (brrs != null && !brrs.isEmpty()) {
          brMatch = true; // il soggetto ha avuto match tramite business rules

          for (BusinessRuleResult brr : brrs)
            brMatched += brr.getBrDescription() + ","; // recupero delle decrizioni delle business rules con un match
        }

        log.info(String.format("Business rule match per %s: %b - %s", drd.getCustId(), brMatch, brMatched));

        List<DetailListMatch> dlms = drd.getListMatches();
        if (dlms != null && !dlms.isEmpty()) {
          for (DetailListMatch d : dlms)
            // se si vogliono i dettagli di ogni singolo match
            log.info("List: " + d.getSlListName() + ", ID: " + d.getSlId() + ", Match " + d.getScore() + "%");
        }
      }

      return ToStringBuilder.reflectionToString(reqResult);

    } catch (Exception e) {
      log.error("Errore nell'esecuzione metodo di scoring!", e);
      return "";
    }
  }
}
