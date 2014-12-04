package com.kolong.tongji.vo;

import java.util.Date;

import com.kolong.tongji.util.Params;

/**
 * 三级指标
 * 
 * @author dfg
 * 
 */
public class ThirdLevelIndicator {
	private int id;
	private int userCount;//发文量
	private int viewCount;//浏览量
	private int reprintCount;//转载量
	private int replyCount;//评论量
	private int titleMinCount;
	private float titleMinWeight;
	private int descMinCount;
	private float descMinWeight;
	private int MinCount;//敏感词数量
	private int sidesCount;//负面观点量
	private int positiveCount;//正面观点量
	private int side_wrong_count;//负面词出现频率
	private int s_sitesCount;
	private int officialInfoCount;
	private float officialWeight;
	private int officialCount;
	private int non_officialInfoCount;
	private float non_officialWeight;
	private int non_officialCount;
	private int countryInfoCount;
	private float countryWeight;
	private int countryCount;
	private int provinceInfoCount;
	private float provinceWeight;
	private int provinceCount;
	private int cityInfoCount;
	private float cityWeight;
	private int cityCount;
	private int xianjiInfoCount;
	private float xianjiWeight;
	private int xianjiCount;
	private int traditionInfoCount;
	private float traditionWeight;
	private int traditionCount;
	private int generalInfoCount;
	private float generalWeight;
	private int generalCount;
	private int socialInfoCount;
	private float socialWeight;
	private int socialCount;
	private int site_yxl;//网站影响力
	private int sitesCount;//网站数量
	private Date rq;
	private int userId;
	
	private double minCountIndex;
	/**
	 * 敏感词数量指数
	 * @return
	 */
	public double getMinCountIndex() {
		minCountIndex = ((this.getMinCount()-Params.get("minMinCount"))*100.0)/(Params.get("maxMinCount")-Params.get("minMinCount"));
		return minCountIndex;
	}
	
	private double minLoc;
	/**
	 * 敏感词位置
	 */
	public double getMinLoc() {
		this.minLoc = this.titleMinCount*this.titleMinWeight + this.descMinCount*this.descMinWeight;
		return this.minLoc;
	}
	private double minLocIndex;
	/**
	 * 敏感词位置指数
	 */
	public double getMinLocIndex() {
		this.minLocIndex = ((this.getMinLoc()-Params.get("minLoc"))*100.0)/(Params.get("maxLoc")-Params.get("minLoc"));
		return this.minLocIndex;
	}
	
	private double sidesCountIndex;
	/**
	 * 负面观点量指数
	 * @return
	 */
	public double getSidesCountIndex() {
		sidesCountIndex = ((this.sidesCount-Params.get("minSidesCount"))*100.0)/(Params.get("maxSidesCount")-Params.get("minSidesCount"));
		return sidesCountIndex;
	}
	private double downSide;
	/**
	 * 负面观点率
	 */
	public double getDownSide() {
		this.downSide = (double)this.sidesCount/this.userCount;
		return this.downSide;
	}
	private double downSideIndex;
	/**
	 * 负面观点率指数
	 * @return
	 */
	public double getDownSideIndex() {
		downSideIndex = ((this.getDownSide()-Params.get("minDownSide"))*100.0)/(Params.get("maxDownSide")-Params.get("minDownSide"));
		return downSideIndex;
	}
	
	
	private double q_overlayCapacity;
	/**
	 * 权威网站覆盖量
	 */
	public double getQ_OverlayCapacity() {
		this.q_overlayCapacity = this.officialInfoCount*this.officialWeight + this.non_officialInfoCount*this.non_officialWeight;
		return this.q_overlayCapacity;
	}
	
	private double q_overlayCapacityIndex;
	/**
	 * 权威网站覆盖量指数
	 */
	public double getQ_overlayCapacityIndex() {
		q_overlayCapacityIndex = ((getQ_OverlayCapacity()-Params.get("minQ_overlayCapacity"))*100.0)/(Params.get("maxQ_overlayCapacity")-Params.get("minQ_overlayCapacity"));
		return q_overlayCapacityIndex;
	}
	
	private double q_coverageRate;
	/**
	 * 权威网站覆盖率
	 */
	public double getQ_CoverageRate() {
		this.q_coverageRate = (this.officialCount*this.officialWeight + this.non_officialCount*this.non_officialWeight)/(double)this.s_sitesCount;
		return this.q_coverageRate;
	}
	
	private double q_coverageRateIndex;
	/**
	 * 权威网站覆盖率指数
	 */
	public double getQ_coverageRateIndex() {
		q_coverageRateIndex = ((getQ_CoverageRate()-Params.get("minQ_coverageRate"))*100.0)/(Params.get("maxQ_coverageRate")-Params.get("minQ_coverageRate"));
		return q_coverageRateIndex;
	}
	
	private double a_overlayCapacity;
	/**
	 * 区域覆盖量
	 */
	public double getA_OverlayCapacity() {
		this.a_overlayCapacity = this.countryInfoCount*this.countryWeight + this.provinceInfoCount*this.provinceWeight + this.cityInfoCount*this.cityWeight + this.xianjiInfoCount*this.xianjiWeight;
		return this.a_overlayCapacity;
	}
	private double a_overlayCapacityIndex;
	/**
	 * 区域覆盖量指数
	 */
	public double getA_overlayCapacityIndex() {
		this.a_overlayCapacityIndex = ((getA_OverlayCapacity()-Params.get("minA_overlayCapacity"))*100.0)/(Params.get("maxA_overlayCapacity")-Params.get("minA_overlayCapacity"));
		return a_overlayCapacityIndex;
	}
	
	private double a_coverageRate;
	/**
	 * 区域覆盖率
	 */
	public double getA_CoverageRate() {
		this.a_coverageRate = (this.countryCount*this.countryWeight + this.provinceCount*this.provinceWeight + this.cityCount*this.cityWeight + this.xianjiCount*this.xianjiWeight)/(double)this.s_sitesCount;
		return this.a_coverageRate;
	}
	private double a_coverageRateIndex;
	/**
	 * 区域覆盖率指数
	 */
	public double getA_coverageRateIndex() {
		a_coverageRateIndex = ((getA_CoverageRate()-Params.get("minA_coverageRate"))*100.0)/(Params.get("maxA_coverageRate")-Params.get("minA_coverageRate"));
		return a_coverageRateIndex;
	}
	
	private double t_overlayCapacity;
	/**
	 * 类型覆盖量
	 */
	public double getT_OverlayCapacity() {
		this.t_overlayCapacity = this.traditionInfoCount*this.traditionWeight + this.generalInfoCount*this.generalWeight + this.socialInfoCount*this.socialWeight;
		return this.t_overlayCapacity;
	}
	private double t_overlayCapacityIndex;
	/**
	 * 类型覆盖量指数
	 */
	public double getT_overlayCapacityIndex() {
		this.t_overlayCapacityIndex = ((getT_OverlayCapacity()-Params.get("minT_overlayCapacity"))*100.0)/(Params.get("maxT_overlayCapacity")-Params.get("minT_overlayCapacity"));
		return t_overlayCapacityIndex;
	}
	private double t_coverageRate;
	/**
	 * 类型覆盖率
	 */
	public double getT_CoverageRate() {
		this.t_coverageRate = (this.traditionCount*this.traditionWeight + this.generalCount*this.generalWeight + this.socialCount*this.socialWeight)/(double)this.s_sitesCount;
		return this.t_coverageRate;
	}
	private double t_coverageRateIndex;
	/**
	 * 类型覆盖率指数
	 */
	public double getT_coverageRateIndex() {
		t_coverageRateIndex = ((getT_CoverageRate()-Params.get("minT_coverageRate"))*100.0)/(Params.get("maxT_coverageRate")-Params.get("minT_coverageRate"));
		return t_coverageRateIndex;
	}
	
//	public void init() {
//		getMinCountIndex();
//		getMinLocIndex();
//		getSidesCountIndex();
//		getDownSideIndex();
//		getQ_overlayCapacityIndex();
//		getQ_coverageRateIndex();
//		getA_overlayCapacityIndex();
//		getA_coverageRateIndex();
//		getT_overlayCapacityIndex();
//		getT_coverageRateIndex();
//	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public int getReprintCount() {
		return reprintCount;
	}
	public void setReprintCount(int reprintCount) {
		this.reprintCount = reprintCount;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public int getTitleMinCount() {
		return titleMinCount;
	}
	public void setTitleMinCount(int titleMinCount) {
		this.titleMinCount = titleMinCount;
	}
	public float getTitleMinWeight() {
		return titleMinWeight;
	}
	public void setTitleMinWeight(float titleMinWeight) {
		this.titleMinWeight = titleMinWeight;
	}
	public int getDescMinCount() {
		return descMinCount;
	}
	public void setDescMinCount(int descMinCount) {
		this.descMinCount = descMinCount;
	}
	public float getDescMinWeight() {
		return descMinWeight;
	}
	public void setDescMinWeight(float descMinWeight) {
		this.descMinWeight = descMinWeight;
	}
	public int getMinCount() {
		return MinCount;
	}
	public void setMinCount(int minCount) {
		MinCount = minCount;
	}
	public int getSidesCount() {
		return sidesCount;
	}
	public void setSidesCount(int sidesCount) {
		this.sidesCount = sidesCount;
	}
	public int getS_sitesCount() {
		return s_sitesCount;
	}
	public void setS_sitesCount(int s_sitesCount) {
		this.s_sitesCount = s_sitesCount;
	}
	public int getOfficialInfoCount() {
		return officialInfoCount;
	}
	public void setOfficialInfoCount(int officialInfoCount) {
		this.officialInfoCount = officialInfoCount;
	}
	public float getOfficialWeight() {
		return officialWeight;
	}
	public void setOfficialWeight(float officialWeight) {
		this.officialWeight = officialWeight;
	}
	public int getOfficialCount() {
		return officialCount;
	}
	public void setOfficialCount(int officialCount) {
		this.officialCount = officialCount;
	}
	public int getNon_officialInfoCount() {
		return non_officialInfoCount;
	}
	public void setNon_officialInfoCount(int non_officialInfoCount) {
		this.non_officialInfoCount = non_officialInfoCount;
	}
	public float getNon_officialWeight() {
		return non_officialWeight;
	}
	public void setNon_officialWeight(float non_officialWeight) {
		this.non_officialWeight = non_officialWeight;
	}
	public int getNon_officialCount() {
		return non_officialCount;
	}
	public void setNon_officialCount(int non_officialCount) {
		this.non_officialCount = non_officialCount;
	}
	public int getCountryInfoCount() {
		return countryInfoCount;
	}
	public void setCountryInfoCount(int countryInfoCount) {
		this.countryInfoCount = countryInfoCount;
	}
	public float getCountryWeight() {
		return countryWeight;
	}
	public void setCountryWeight(float countryWeight) {
		this.countryWeight = countryWeight;
	}
	public int getCountryCount() {
		return countryCount;
	}
	public void setCountryCount(int countryCount) {
		this.countryCount = countryCount;
	}
	public int getProvinceInfoCount() {
		return provinceInfoCount;
	}
	public void setProvinceInfoCount(int provinceInfoCount) {
		this.provinceInfoCount = provinceInfoCount;
	}
	public float getProvinceWeight() {
		return provinceWeight;
	}
	public void setProvinceWeight(float provinceWeight) {
		this.provinceWeight = provinceWeight;
	}
	public int getProvinceCount() {
		return provinceCount;
	}
	public void setProvinceCount(int provinceCount) {
		this.provinceCount = provinceCount;
	}
	public int getCityInfoCount() {
		return cityInfoCount;
	}
	public void setCityInfoCount(int cityInfoCount) {
		this.cityInfoCount = cityInfoCount;
	}
	public float getCityWeight() {
		return cityWeight;
	}
	public void setCityWeight(float cityWeight) {
		this.cityWeight = cityWeight;
	}
	public int getCityCount() {
		return cityCount;
	}
	public void setCityCount(int cityCount) {
		this.cityCount = cityCount;
	}
	public int getXianjiInfoCount() {
		return xianjiInfoCount;
	}
	public void setXianjiInfoCount(int xianjiInfoCount) {
		this.xianjiInfoCount = xianjiInfoCount;
	}
	public float getXianjiWeight() {
		return xianjiWeight;
	}
	public void setXianjiWeight(float xianjiWeight) {
		this.xianjiWeight = xianjiWeight;
	}
	public int getXianjiCount() {
		return xianjiCount;
	}
	public void setXianjiCount(int xianjiCount) {
		this.xianjiCount = xianjiCount;
	}
	public int getTraditionInfoCount() {
		return traditionInfoCount;
	}
	public void setTraditionInfoCount(int traditionInfoCount) {
		this.traditionInfoCount = traditionInfoCount;
	}
	public float getTraditionWeight() {
		return traditionWeight;
	}
	public void setTraditionWeight(float traditionWeight) {
		this.traditionWeight = traditionWeight;
	}
	public int getTraditionCount() {
		return traditionCount;
	}
	public void setTraditionCount(int traditionCount) {
		this.traditionCount = traditionCount;
	}
	public int getGeneralInfoCount() {
		return generalInfoCount;
	}
	public void setGeneralInfoCount(int generalInfoCount) {
		this.generalInfoCount = generalInfoCount;
	}
	public float getGeneralWeight() {
		return generalWeight;
	}
	public void setGeneralWeight(float generalWeight) {
		this.generalWeight = generalWeight;
	}
	public int getGeneralCount() {
		return generalCount;
	}
	public void setGeneralCount(int generalCount) {
		this.generalCount = generalCount;
	}
	public int getSocialInfoCount() {
		return socialInfoCount;
	}
	public void setSocialInfoCount(int socialInfoCount) {
		this.socialInfoCount = socialInfoCount;
	}
	public float getSocialWeight() {
		return socialWeight;
	}
	public void setSocialWeight(float socialWeight) {
		this.socialWeight = socialWeight;
	}
	public int getSocialCount() {
		return socialCount;
	}
	public void setSocialCount(int socialCount) {
		this.socialCount = socialCount;
	}
	public int getSite_yxl() {
		return site_yxl;
	}
	public void setSite_yxl(int site_yxl) {
		this.site_yxl = site_yxl;
	}
	public int getSitesCount() {
		return sitesCount;
	}
	public void setSitesCount(int sitesCount) {
		this.sitesCount = sitesCount;
	}
	public Date getRq() {
		return rq;
	}
	public void setRq(Date rq) {
		this.rq = rq;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPositiveCount() {
		return positiveCount;
	}

	public void setPositiveCount(int positiveCount) {
		this.positiveCount = positiveCount;
	}

	public int getSide_wrong_count() {
		return side_wrong_count;
	}

	public void setSide_wrong_count(int side_wrong_count) {
		this.side_wrong_count = side_wrong_count;
	}

}
