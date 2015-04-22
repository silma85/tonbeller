package it.racomputer.sso;

import javax.servlet.http.HttpServletRequest;

import com.tonbeller.tblogin.extern.ExternalLogin;
import com.tonbeller.tblogin.extern.ExternalLoginOk;
import com.tonbeller.tblogin.extern.ExternalLoginResult;

public class XSSOLoginMock implements ExternalLogin {
	public ExternalLoginResult loginExtern(HttpServletRequest arg0,	String arg1, char[] arg2) throws Exception {
		System.out.println("External Login called. Not managed!");
		
		return null;
	}

	public ExternalLoginResult loginExternSSO(HttpServletRequest arg0) throws Exception {
		System.out.println("ExternalSSO - Authentication...");
		
		ExternalLoginOk loginOK = new ExternalLoginOk();
		loginOK.setUser("Demo");
		//loginOK.setClientId("0001");
		//loginOK.setInitGrp("SironAML");
		//loginOK.setOrgUnit(Integer.valueOf(0));
		loginOK.setRoles("S");
		
		System.out.println("ExternalSSO - Authenticated");
		
		return loginOK;
	}

}
