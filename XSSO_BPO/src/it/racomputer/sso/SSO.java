package it.racomputer.sso;

import it.abaco.xsso.client.XSSOClientImpl;
import it.abaco.xsso.model.DatiPortaleModel;
import it.abaco.xsso.model.HSSOCredentialsModel;
import it.abaco.xsso.model.ISSOCredentialsModel;
import it.abaco.xsso.model.PostoDiLavoroModel;
import it.abaco.xsso.model.XSSOAreaModel;

import java.io.FileInputStream;

public class SSO {
	public void authenticate() {
		System.out.println("BPO - Autenticazione...");
		
		try {
			String key = "C:\\Costa\\Clienti\\BPO\\SSO\\TEST_LUCA_key.bin";
			String receivedData = "9J5jBUo12nkuLoA0vI7FznCeoWWGMAZAVxetD/h7DfR8JLmkY6TMXy83NVpGQU0v7HoF9szhEf37kuTS3hzsx246PmTPS/b/l25Zzs/DF4W0u8obHIEYs3WgEP5i2OXv9NHpEaCaHzGB2lOQ5XWe6AdOK8eT6sbEuPKu9ozTReTzj1cHMmIDVM9L1PExR4z16sJAbnkEVHIiEnp5MGIsrfadsLeN/DqJ81RSAgtB8rI60WxSS5RLQcGC4rJgPjtaPQjLmudhe+bY4cjE9JuMa7dR6ygW/c58QABDSDACbf8TEUUwqi1BtJJ5Qi8cCWF3bJdg+fJoPK8VRzUlpXS+EZMWfymggZCM/6J2LOFcsq1yTl34FrzlIFolZ7lS4Fgb3qBBgum3DNraAuA3ZAUSdQvt7/2UBEjgSDA57Nl4NhAXhz0/OFO4y1Ych0kpEPQm7fI/tVu2sdAka4/N1NSUcxw0Kr9aM6RAN1l6M7t+dRZdJ4ve0BP3uDgjob565P0+aNpXMZgWaLC+k6h0RE23WirwM9M8xb3GUL8yg/eoALF3ryE2gqdIe9886znh8uw32Z0/AiRcB+uHUrBavUzRe98c5LBmwrwqfvVn4v+uCRxwxAApTWuTwtPBsrk6bma5CA2QJWvYoBr7GFMZsHL5pPacZlOY+LcftnD7s0zQAXKd2HL3iKy9vNoR62rhCHtozoEzfBc2Ma6g6XL5gdly6AojhiIMhmVc16SsvTUElnfjzdNNcAzTHYtFRxNXAj3y6qpOI284llLGfX3zGbsZLBXoamR1QA2z99FMBIfHBMzYeI/dufF003tDRn2qNa4AzYheuHf350dq7Vm7gzBdHfQz7uKHiWypfEfMCmTZKeLwNR2GMkqGRBqIvF0KQCUxAbeKf8NGwflBvhiksVeJlhaFmOXv030JWOGK9RITjjfMX1NPHlZPGDNsmt2zE1O0jUTS1eZfXchsJsaDHqk/LGQRLhqw+Z0L/egy3kO6IEBqRxktHNX+jA4tfLuB2msbkn7BwrJkJiHomBTtwkDsGRYlOnaspvWAlxl1TItHxXxPohVZcg6rPjzzpSMHKO6cEHHlFjJZQhrYIqegwsUSVHMoFlm6QOLJG1c/EbR1qMBqHy3lOyx/gkybnaCvuTZO6fwnyCfYAf5EAaZIx6inc7kqUebZrpWY6nBs41UPVukfyGclkW/WbCt/WC7DMs3GHhXYE2YVs733rCD8f2sXw3aHzUSi5Fk+OcGaDrKd+v4uYD1eBC3TZ3LhnF3AsZaK";
			
			System.out.println("authenticate : " + key);
			
			FileInputStream fis = new FileInputStream(key);
			byte[] cipherKey = new byte[fis.available()];
			fis.read(cipherKey);
			fis.close();
			
			XSSOClientImpl xssoClient = new XSSOClientImpl();
			xssoClient.setCipherKey(cipherKey);
			xssoClient.setCipherMode("A");
			xssoClient.setCheckTimestamp(false);
			//xssoClient.setCheckTokenId(true);
			xssoClient.setReceivedData(receivedData);
			xssoClient.process();
			XSSOAreaModel			xa = xssoClient.getXSSOArea();
			HSSOCredentialsModel 	hc = xssoClient.getHSSOCredentials();
			DatiPortaleModel 		dp = xssoClient.getDatiPortale();
			ISSOCredentialsModel 	ic = xssoClient.getISSOCredentials();
			PostoDiLavoroModel 		pl = xssoClient.getPostoDiLavoro();
			String 					extra = xssoClient.getExtraParamText();
			
			
			
			System.out.println("BPO - Autenticato");
		
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
