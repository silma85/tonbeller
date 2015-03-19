package it.sia.tonbeller.customersearch.mock;

import it.sia.tonbeller.customersearch.domain.CustomerData;

import java.util.ArrayList;
import java.util.List;

public class MockCustomerList {

  public static List<CustomerData> list() {

    List<CustomerData> list = new ArrayList<CustomerData>();

    list.add(new CustomerData("12398723892", "PPIUTY"));
    list.add(new CustomerData("85454sdd3892", "MABANE"));
    list.add(new CustomerData("12398723892", "AHAJHA"));
    list.add(new CustomerData("14F235292", "1M1TR4N"));
    list.add(new CustomerData("123Q32FF4F3", "75365"));
    list.add(new CustomerData("12398723892", "N4343B"));
    list.add(new CustomerData("DGEREG723892", "N332B"));

    return list;
  }

}
