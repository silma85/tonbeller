/**
 * WSSearchService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.sia.stresstest.services.wssearch;

public interface WSSearchService extends javax.xml.rpc.Service {
  public java.lang.String getWSSearchAddress();

  public WSSearch getWSSearch() throws javax.xml.rpc.ServiceException;

  public WSSearch getWSSearch(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
