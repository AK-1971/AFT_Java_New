package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GeoIpServiceTest {
  @Test
  public void testMyIP() {
    String geoIP = new GeoIPService().getGeoIPServiceSoap12().getIpLocation20("46.216.28.113");
    String geoIPcountry = null;
    if (geoIP.contains("BY")) {
      geoIPcountry = "BY";
    }
    Assert.assertEquals(geoIPcountry, "BY");
  }
}
