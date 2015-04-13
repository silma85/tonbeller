package it.sia.tonbeller.customersearch.business.domain;

import lombok.Data;

@Data
public class CustomerDocument {

  private String name;

  private byte[] base64;
}
