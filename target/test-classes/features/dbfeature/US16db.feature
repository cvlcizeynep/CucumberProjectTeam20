
Feature: Vicedean mesaj sorgulama

  @db16
  Scenario: TC01 Vice dean database ile mesajlari gorulebilmelidir.
    *  Kullanici viceDean olarak login olur
    * Kullanici Contact butonuna tiklar
    * Kullanici Your Name textboxini  gecerli yourname bilgisini girer
    * Kullanici Your Mail textboxini  gecerli mailini girer
    * Kullanici Subject textboxini doldurur
    * Kullanici Message textboxini doldurur
    * Kullanici Send Message butonuna tiklar
    When Databaseden olusturulan mesaj maille sorgulanir
    When Databaseden olusturulan mesaj isimle sorgulanir

  Scenario: TC02 Vicedean mesaj silebilmelidir
      #bizim silme yetkimiz olmadigi icin yapilmadi
