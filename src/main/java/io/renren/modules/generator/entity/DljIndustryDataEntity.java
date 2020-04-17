package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 行业数据表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-17 15:18:41
 */
@Data
@TableName("dlj_industry_data")
public class DljIndustryDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 行业id
	 */
	private String industryName;
	/**
	 * 用户数
	 */
	private String userNum;
	/**
	 * 装机容量
	 */
	private String installedCapacity;
	/**
	 * 用电量本月
	 */
	private String eleConMonth;
	/**
	 * 用电量上年同月
	 */
	private String eleConOldYearMonth;
	/**
	 * 本月同比
	 */
	private String compareMonth;
	/**
	 * 用电量本年
	 */
	private String eleConYear;
	/**
	 * 用电量上年
	 */
	private String eleConOldYear;
	/**
	 * 本年同比
	 */
	private String assYear;
	/**
	 * 行业产能利用率
	 */
	private String industryCapUtil;
	/**
	 * 记录时间
	 */
	private Date recordTime;
	/**
	 * 环比
	 */
	private String chainRatio;

}
