package com.kolong.tongji.factory;

import com.kolong.tongji.dao.Lt_TongJiDao;
import com.kolong.tongji.dao.TableDao;
import com.kolong.tongji.dao.ThirdDao;
import com.kolong.tongji.dao.Wb_TongJiDao;

public class DaoFactory {
	private static ThirdDao thirdDao;
	private static TableDao tableDao;
	private static Wb_TongJiDao wb_TongJiDao;
	private static Lt_TongJiDao lt_TongJiDao;
	
	public static ThirdDao getThirdDao() {
		if(thirdDao == null) {
			thirdDao = new ThirdDao();
		}
		return thirdDao;
	}
	
	public static TableDao getTableDao() {
		if(tableDao == null) {
			tableDao = new TableDao();
		}
		return tableDao;
	}
	
	public static Wb_TongJiDao getWb_TongJiDao() {
		if(wb_TongJiDao == null) {
			wb_TongJiDao = new Wb_TongJiDao();
		}
		return wb_TongJiDao;
	}
	
	public static Lt_TongJiDao getLt_TongJiDao() {
		if(lt_TongJiDao == null) {
			lt_TongJiDao = new Lt_TongJiDao();
		}
		return lt_TongJiDao;
	}
}
