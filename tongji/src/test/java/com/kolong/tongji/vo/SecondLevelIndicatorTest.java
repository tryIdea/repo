package com.kolong.tongji.vo;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SecondLevelIndicatorTest extends TestCase {
	SecondLevelIndicator second;

	protected void setUp() throws Exception {
		ThirdLevelIndicator third = new ThirdLevelIndicator();
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
		second = new SecondLevelIndicator(third);
	}

	protected void tearDown() throws Exception {
		second = null;
	}

	public void testGetInfoIndex() {
		Assert.assertEquals(30.4182, second.getInfoIndex(),0.0001);
	}

	public void testGetViewIndex() {
		Assert.assertEquals(0.3075, second.getViewIndex(),0.0001);
	}

	public void testGetReprintIndex() {
		Assert.assertEquals(100, second.getReprintIndex(),0.0001);
	}

	public void testGetReplyIndex() {
		Assert.assertEquals(19.8170, second.getReplyIndex(),0.0001);
	}

	public void testGetContIndex() {
		Assert.assertEquals(2.2365, second.getContIndex(),0.0001);
	}

	public void testGetOpinionIndex() {
		Assert.assertEquals(1.8327, second.getOpinionIndex(),0.0001);
	}

	public void testGetSiteIndex() {
		Assert.assertEquals(0.6276, second.getSiteIndex(),0.0001);
	}

	public void testGetAreaIndex() {
		Assert.assertEquals(0.1889, second.getAreaIndex(),0.0001);
	}

	public void testGetTypeIndex() {
		Assert.assertEquals(0.1918, second.getTypeIndex(),0.0001);
	}

	public void testGetInfluIndex() {
		Assert.assertEquals(6, second.getInfluIndex(),0.0001);
	}

	public void testGetNumIndex() {
		Assert.assertEquals(26.5604, second.getNumIndex(),0.0001);
	}

}
