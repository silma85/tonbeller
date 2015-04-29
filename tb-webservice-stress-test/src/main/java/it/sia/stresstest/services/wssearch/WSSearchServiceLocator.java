/**
 * WSSearchServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.sia.stresstest.services.wssearch;

public class WSSearchServiceLocator extends org.apache.axis.client.Service implements WSSearchService {

  private static final long serialVersionUID = -3202962337657677690L;

  public WSSearchServiceLocator() {
  }

  public WSSearchServiceLocator(org.apache.axis.EngineConfiguration config) {
    super(config);
  }

  public WSSearchServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
    super(wsdlLoc, sName);
  }

  // Use to get a proxy class for WSSearch
  private java.lang.String WSSearch_address = "http://localhost:8585/kyc/services/WSSearch";

  public java.lang.String getWSSearchAddress() {
    return WSSearch_address;
  }

  // The WSDD service name defaults to the port name.
  private java.lang.String WSSearchWSDDServiceName = "WSSearch";

  public java.lang.String getWSSearchWSDDServiceName() {
    return WSSearchWSDDServiceName;
  }

  public void setWSSearchWSDDServiceName(java.lang.String name) {
    WSSearchWSDDServiceName = name;
  }

  public WSSearch getWSSearch() throws javax.xml.rpc.ServiceException {
    java.net.URL endpoint;
    try {
      endpoint = new java.net.URL(WSSearch_address);
    } catch (java.net.MalformedURLException e) {
      throw new javax.xml.rpc.ServiceException(e);
    }
    return getWSSearch(endpoint);
  }

  public WSSearch getWSSearch(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
    try {
      WSSearchSoapBindingStub _stub = new WSSearchSoapBindingStub(
          portAddress, this);
      _stub.setPortName(getWSSearchWSDDServiceName());
      return _stub;
    } catch (org.apache.axis.AxisFault e) {
      return null;
    }
  }

  public void setWSSearchEndpointAddress(java.lang.String address) {
    WSSearch_address = address;
  }

  /**
   * For the given interface, get the stub implementation. If this service has no port for the given interface, then
   * ServiceException is thrown.
   */
  public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
    try {
      if (WSSearch.class.isAssignableFrom(serviceEndpointInterface)) {
        WSSearchSoapBindingStub _stub = new WSSearchSoapBindingStub(
            new java.net.URL(WSSearch_address), this);
        _stub.setPortName(getWSSearchWSDDServiceName());
        return _stub;
      }
    } catch (java.lang.Throwable t) {
      throw new javax.xml.rpc.ServiceException(t);
    }
    throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
        + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
  }

  /**
   * For the given interface, get the stub implementation. If this service has no port for the given interface, then
   * ServiceException is thrown.
   */
  public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface)
      throws javax.xml.rpc.ServiceException {
    if (portName == null) {
      return getPort(serviceEndpointInterface);
    }
    java.lang.String inputPortName = portName.getLocalPart();
    if ("WSSearch".equals(inputPortName)) {
      return getWSSearch();
    }
    else {
      java.rmi.Remote _stub = getPort(serviceEndpointInterface);
      ((org.apache.axis.client.Stub) _stub).setPortName(portName);
      return _stub;
    }
  }

  public javax.xml.namespace.QName getServiceName() {
    return new javax.xml.namespace.QName("http://localhost:8585/kyc/services/WSSearch", "WSSearchService");
  }

  private java.util.HashSet ports = null;

  public java.util.Iterator getPorts() {
    if (ports == null) {
      ports = new java.util.HashSet();
      ports.add(new javax.xml.namespace.QName("http://localhost:8585/kyc/services/WSSearch", "WSSearch"));
    }
    return ports.iterator();
  }

  /**
   * Set the endpoint address for the specified port name.
   */
  public void setEndpointAddress(java.lang.String portName, java.lang.String address)
      throws javax.xml.rpc.ServiceException {

    if ("WSSearch".equals(portName)) {
      setWSSearchEndpointAddress(address);
    }
    else
    { // Unknown Port Name
      throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
    }
  }

  /**
   * Set the endpoint address for the specified port name.
   */
  public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address)
      throws javax.xml.rpc.ServiceException {
    setEndpointAddress(portName.getLocalPart(), address);
  }

}
