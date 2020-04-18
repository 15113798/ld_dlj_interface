package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 行业关系表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-04-17 15:05:46
 */
@Data
@TableName("dlj_industry")
public class DljIndustryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 行业名称
	 */
	private String name;
	/**
	 * Excel中的行业名称
	 */
	private String overName;
	/**
	 * 父级id
	 */
	private Integer pid;

	private Integer is_ind;

}
