package com.kolong.tongji.vo;

import com.kolong.tongji.util.Params;

/**
 * 二级指标
 * @author dfg
 *
 */
public class SecondLevelIndicator {
	//三级指标
	public ThirdLevelIndicator third;
	
	public SecondLevelIndicator(ThirdLevelIndicator third) {
		this.third = third;
	}
	
	//发文量指数
	private double infoIndex;
	//浏览量指数
	private double viewIndex;
	//转载量指数
	private double reprintIndex;
	//评论量指数
	private double replyIndex;
	//内容敏感性指数
	private double contIndex;
	//观点倾向性指数
	private double opinionIndex;
	//网站权威性指数
	private double siteIndex;
	//区域全面性指数
	private double areaIndex;
	//类型全面性指数
	private double typeIndex;
	//网站影响力指数
	private double influIndex;
	//网站数量指数
	private double numIndex;

	/**
	 * 发文量指数
	 * @return
	 */
	public double getInfoIndex() {
		infoIndex = ((third.getUserCount()-Params.get("minInfo"))*100.0)/(Params.get("maxInfo")-Params.get("minInfo"));
		return infoIndex;
	}
	
	/**
	 * 浏览量指数
	 * @return
	 */
	public double getViewIndex() {
		viewIndex = ((third.getViewCount()-Params.get("minView"))*100.0)/(Params.get("maxView")-Params.get("minView"));
		return viewIndex;
	}
	/**
	 * 转载量指数
	 * @return
	 */
	public double getReprintIndex() {
		reprintIndex = ((third.getReprintCount()-Params.get("minReprint"))*100.0)/(Params.get("maxReprint")-Params.get("minReprint"));
		return reprintIndex;
	}
	/**
	 * 评论量指数
	 * @return
	 */
	public double getReplyIndex() {
		replyIndex = ((third.getReplyCount()-Params.get("minReply"))*100.0)/(Params.get("maxReply")-Params.get("minReply"));
		return replyIndex;
	}
	/**
	 * 内容敏感性指数
	 * @return
	 */
	public double getContIndex() {
		contIndex = third.getMinLocIndex()*Params.get("minLocWeight") + third.getMinCountIndex()*Params.get("minCountWeight");
		return contIndex;
	}
	/**
	 * 观点倾向性指数
	 * @return
	 */
	public double getOpinionIndex() {
		opinionIndex = third.getSidesCountIndex()*Params.get("sidesCountWeight") + third.getDownSideIndex()*Params.get("downSideWeight");
		return opinionIndex;
	}
	/**
	 * 网站权威性指数
	 * @return
	 */
	public double getSiteIndex() {
		siteIndex = third.getQ_overlayCapacityIndex()*Params.get("q_overlayCapacityWeight") + third.getQ_coverageRateIndex()*Params.get("q_coverageRateWeight");
		return siteIndex;
	}
	/**
	 * 区域全面性指数
	 * @return
	 */
	public double getAreaIndex() {
		areaIndex = third.getA_overlayCapacityIndex()*Params.get("a_overlayCapacityWeight") + third.getA_coverageRateIndex()*Params.get("a_coverageRateWeight");
		return areaIndex;
	}
	/**
	 * 类型全面性指数
	 * @return
	 */
	public double getTypeIndex() {
		typeIndex = third.getT_overlayCapacityIndex()*Params.get("t_overlayCapacityWeight") + third.getT_coverageRateIndex()*Params.get("t_coverageRateWeight");
		return typeIndex;
	}
	/**
	 * 网站影响力指数
	 * @return
	 */
	public double getInfluIndex() {
		influIndex = ((third.getSite_yxl()-Params.get("minSite_yxl"))*100.0)/(Params.get("maxSite_yxl")-Params.get("minSite_yxl"));
		return influIndex;
	}
	/**
	 * 网站数量指数
	 * @return
	 */
	public double getNumIndex() {
		numIndex = ((third.getSitesCount()-Params.get("minSitesCount"))*100.0)/(Params.get("maxSitesCount")-Params.get("minSitesCount"));
		return numIndex;
	}
}
