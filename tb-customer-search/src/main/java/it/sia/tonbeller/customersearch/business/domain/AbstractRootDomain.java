/*
 * *****************************************************************
 * Copyright (c) 2014, Ra Computer All rights reserved.*****************************************************************
 */

package it.sia.tonbeller.customersearch.business.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractRootDomain implements Serializable {

  private static final long serialVersionUID = 7238698337635486701L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`ID`")
  protected long id = -1;

}
