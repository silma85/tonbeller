package it.sia.stresstest.services.wssearch;

public class WSSearchProxy implements WSSearch {
  private String _endpoint = null;
  private WSSearch wSSearch = null;

  public WSSearchProxy() {
    _initWSSearchProxy();
  }

  public WSSearchProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSSearchProxy();
  }

  private void _initWSSearchProxy() {
    try {
      wSSearch = (new WSSearchServiceLocator()).getWSSearch();
      if (wSSearch != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub) wSSearch)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String) ((javax.xml.rpc.Stub) wSSearch)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    } catch (javax.xml.rpc.ServiceException serviceException) {
    }
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSSearch != null)
      ((javax.xml.rpc.Stub) wSSearch)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public WSSearch getWSSearch() {
    if (wSSearch == null)
      _initWSSearchProxy();
    return wSSearch;
  }

  public byte[] doSearch(byte[] input) throws java.rmi.RemoteException {
    if (wSSearch == null)
      _initWSSearchProxy();
    return wSSearch.doSearch(input);
  }

}