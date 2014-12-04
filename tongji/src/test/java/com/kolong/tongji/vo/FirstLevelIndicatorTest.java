package com.kolong.tongji.vo;

import junit.framework.Assert;
import junit.framework.TestCase;

public class FirstLevelIndicatorTest extends TestCase {
	FirstLevelIndicator first;

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
		SecondLevelIndicator second = new SecondLevelIndicator(third);
		
		first = new FirstLevelIndicator(second);
	}

	protected void tearDown() throws Exception {
		first = null;
	}

	public void testGetExposIndex() {
		Assert.assertEquals(14.7589, first.getExposIndex(), 0.0001);
	}

	public void testGetParticipationIndex() {
		Assert.assertEquals(8.3911, first.getParticipationIndex(), 0.0001);
	}

	public void testGetPublicOpinionIndex() {
		Assert.assertEquals(0.5393, first.getPublicOpinionIndex(), 0.0001);
	}

	public void testGetSiteFeatureIndex() {
		Assert.assertEquals(0.6536, first.getSiteFeatureIndex(), 0.0001);
	}

}
