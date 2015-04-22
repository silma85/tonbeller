package it.racomputer.sso;

import it.abaco.sso.util.Costanti;
import it.abaco.xsso.client.XSSOClientImpl;
import it.abaco.xsso.model.DatiPortaleModel;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.tonbeller.tblogin.extern.ExternalLogin;
import com.tonbeller.tblogin.extern.ExternalLoginOk;
import com.tonbeller.tblogin.extern.ExternalLoginResult;

public class XSSOLoginBPO_old implements ExternalLogin {
	private static Logger logger = Logger.getLogger(XSSOLoginBPO_old.class);
	
	public ExternalLoginResult loginExtern(HttpServletRequest arg0,	String arg1, char[] arg2) throws Exception {
		logger.info("External Login called. Not managed!");
		
		return null;
	}

	public ExternalLoginResult loginExternSSO(HttpServletRequest req) throws Exception {
		ExternalLoginOk login = null;
		
		try {
			login = new ExternalLoginOk();

			Context initCtx = new InitialContext();
			Context context = (Context)initCtx.lookup("java:comp/env");
			URL binPath = (URL)context.lookup("XSSObinPath");
			DataSource ds = (DataSource)context.lookup("jdbc/TBellerDB");
			
			logger.info("ExternalSSO - Authenticating with " + binPath.getFile());
			
			FileInputStream fis = new FileInputStream(binPath.getFile());
			byte[] cipherKey = new byte[fis.available()];
			fis.read(cipherKey);
			fis.close();

			XSSOClientImpl xssoClient = new XSSOClientImpl();
			xssoClient.setCipherKey(cipherKey);
			xssoClient.setCipherMode(req.getParameter(Costanti.PARAMETRO_XSSO_CIPHER_MODE));
			xssoClient.setReceivedData(req.getParameter(Costanti.PARAMETRO_XSSO_XML_CIFRATO));
			xssoClient.process();
			
//			XSSOAreaModel        xa    = xssoClient.getXSSOArea();
//			HSSOCredentialsModel hc    = xssoClient.getHSSOCredentials();
			DatiPortaleModel     dp    = xssoClient.getDatiPortale();
//			ISSOCredentialsModel ic    = xssoClient.getISSOCredentials();
//			PostoDiLavoroModel   pl    = xssoClient.getPostoDiLavoro();
//			String               extra = xssoClient.getExtraParamText();
			
			String role = getUserRole(dp.getMatricola(), ds);
			
			login.setUser(dp.getMatricola());
			login.setRoles(role);
			
			logger.info("ExternalSSO - Authenticated : " + dp.getMatricola());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			if(login == null) {
				login = new ExternalLoginOk();
				login.setUser("demosso");
				login.setRoles("S");
				
				logger.info("ExternalSSO - Authenticated with DemoSSO user");
			}
		}
		
		return login;
	}

	private String getUserRole(String matricola, DataSource ds) throws Exception {
		String sql = "select BENUTZERTYP from global.GWGBENUT where lower(LOGINNAME) = lower(?) and HISTBIS = '9999'";
		String role = "";
		
		Connection con = ds.getConnection();
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, matricola);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next())
			role = rs.getString(1).trim();
		
		return role;
	}
}
