package com.kolong.tongji.vo;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ThirdLevelIndicatorTest extends TestCase {

	ThirdLevelIndicator third;
	
	protected void setUp() throws Exception {
		third = new ThirdLevelIndicator();
		third.setUserCount(400);
		third.setViewCount(250);
		third.setReprintCount(320);
		third.setReplyCount(130);
		third.setTitleMinCount(20);
		third.setTitleMinWeight(0.7f);
		third.setDescMinCount(35);
		third.setDescMinWeight(0.3f);
		third.setMinCount(150);
		third.setSidesCount(240);
		third.setS_sitesCount(500);
		third.setOfficialInfoCount(260);
		third.setOfficialWeight(0.6f);
		third.setOfficialCount(80);
		third.setNon_officialInfoCount(140);
		third.setNon_officialWeight(0.4f);
		third.setNon_officialCount(120);
		third.setCountryInfoCount(220);
		third.setCountryWeight(0.45f);
		third.setCountryCount(80);
		third.setProvinceInfoCount(110);
		third.setProvinceWeight(0.3f);
		third.setProvinceCount(50);
		third.setCityInfoCount(60);
		third.setCityWeight(0.2f);
		third.setCityCount(60);
		third.setXianjiInfoCount(10);
		third.setXianjiWeight(0.05f);
		third.setXianjiCount(10);
		third.setTraditionInfoCount(150);
		third.setTraditionWeight(0.3f);
		third.setTraditionCount(60);
		third.setGeneralInfoCount(100);
		third.setGeneralWeight(0.3f);
		third.setGeneralCount(80);
		third.setSocialInfoCount(150);
		third.setSocialWeight(0.4f);
		third.setSocialCount(60);
		third.setSite_yxl(300);
		third.setSitesCount(200);
	}

	protected void tearDown() throws Exception {
		third = null;
	}
	
	public void testGetMinLoc() {
		Assert.assertEquals(24.5, third.getMinLoc(),0.0001);
	}

	public void testGetSidesCountIndex() {
		Assert.assertEquals(59.4059, third.getSidesCountIndex(),0.0001);
	}
	public void testGetDownSideIndex() {
		Assert.assertEquals(60, third.getDownSideIndex(),0.0001);
	}

	public void testGetQ_overlayCapacityIndex() {
		Assert.assertEquals(38.5314, third.getQ_overlayCapacityIndex(),0.0001);
	}

	public void testGetQ_coverageRateIndex() {
		Assert.assertEquals(19.2, third.getQ_coverageRateIndex(),0.0001);
	}

	public void testGetA_overlayCapacityIndex() {
		Assert.assertEquals(59.0277, third.getA_overlayCapacityIndex(),0.0001);
	}

	public void testGetA_coverageRateIndex() {
		Assert.assertEquals(12.7, third.getA_coverageRateIndex(),0.0001);
	}

	public void testGetT_overlayCapacityIndex() {
		Assert.assertEquals(33.9622, third.getT_overlayCapacityIndex(),0.0001);
	}

	public void testGetT_coverageRateIndex() {
		Assert.assertEquals(13.2, third.getT_coverageRateIndex(),0.0001);
	}

}
