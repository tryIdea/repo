package com.kolong.tongji.vo;

import com.kolong.tongji.util.Params;

/**
 * 一级指标
 * @author dfg
 *
 */
public class FirstLevelIndicator {
	//二级指标
	public SecondLevelIndicator second;

	public FirstLevelIndicator(SecondLevelIndicator second) {
		this.second = second;
	}
	
	//曝光程度指数
	private double exposIndex;
	//参与程度指数
	private double participationIndex;
	//舆情观点指数
	private double publicOpinionIndex;
	//网站特点指数
	private double siteFeatureIndex;
	
	/**
	 * 曝光程度指数
	 * @return
	 */
	public double getExposIndex() {
		exposIndex = second.getInfoIndex()*Params.get("infoWeight");
		return exposIndex;
	}
	/**
	 * 参与程度指数
	 * @return
	 */
	public double getParticipationIndex() {
		participationIndex = second.getViewIndex()*Params.get("viewWeight") + second.getReprintIndex()*Params.get("reprintWeight") + second.getReplyIndex()*Params.get("replyWeight");
		return participationIndex;
	}
	/**
	 * 舆情观点指数
	 * @return
	 */
	public double getPublicOpinionIndex() {
		publicOpinionIndex = second.getContIndex()*Params.get("contWeight") + second.getOpinionIndex()*Params.get("opinionWeight");
		return publicOpinionIndex;
	}
	
	/**
	 * 网站特点指数
	 * @return
	 */
	public double getSiteFeatureIndex() {
		siteFeatureIndex = second.getSiteIndex()*Params.get("siteWeight") + second.getAreaIndex()*Params.get("areaWeight") + second.getTypeIndex()*Params.get("typeWeight") + second.getInfluIndex()*Params.get("influWeight") + second.getNumIndex()*Params.get("numWeight");
		return siteFeatureIndex;
	}
	
	public class Rate {
		
		//曝光变化率
		private double ExposIndexRate;
		
		public Rate(double exposIndexBefore) {//前一监测周期曝光程度
			getExposIndex();
			ExposIndexRate = (double)(exposIndex-exposIndexBefore)/exposIndexBefore;
		}
		
		public double getExposIndexRate() {
			return ExposIndexRate;
		}
	}
	
}
