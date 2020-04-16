package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 菜单表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-17 00:43:54
 */
@Data
@TableName("dlj_industry_menu")
public class DljIndustryMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 
	 */
	private Integer pid;
	/**
	 * 行业id
	 */
	private Integer industryId;

}
