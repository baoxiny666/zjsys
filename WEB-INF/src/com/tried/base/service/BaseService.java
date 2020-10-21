package com.tried.base.service;

import java.util.List;
import java.util.Map;

import com.tried.common.Page;
public interface BaseService<T> {
	/**
	 *  实体对象托管
		 * @Description 
		 * @author liuxd
		 * @date 2019-3-15 上午10:36:34
		 * @version V1.0
	 */
	public void evict(T entity)throws Exception;
	/** 
	  * @Description 添加对象
	  * @author liuxd
	  * @date 2015-8-1 下午5:01:24
	  * @version V1.0
	 */
	public void add(T entity)throws Exception;
	/**
	  * @Description 根据id删除对象
	  * @author liuxd
	  * @date 2015-8-1 下午5:01:42
	  * @version V1.0
	 */
	public void delete(String id)throws Exception;
	/**
		 * @Description 批量删除
		 * @author liuxd
		 * @date 2018-12-18 下午1:45:29
		 * @version V1.0
	 */
	public void beachDelete(String[] id)throws Exception;
	/**
	  * @Description 删除对象
	  * @author liuxd
	  * @date 2016-6-15 上午8:47:11
	  * @version V1.0
	 */
	public void delete(T entity)throws Exception;
	/**
	  * @Description 修改对象
	  * @author liuxd
	  * @date 2015-8-1 下午5:01:53
	  * @version V1.0
	 */
	public void update(T entity)throws Exception;
	/**
	  * @Description 根据id获取对象
	  * @author liuxd
	  * @date 2015-8-1 下午5:02:10
	  * @version V1.0
	 */
	public T getById(String id)throws Exception;
	
	/**
	 * 获取第一条记录，根据field排序
	 */
	public T getFirstRecordByField(final String hql) throws Exception;
	
	/**
	 * 获取前NUM 数量的记录，根据field排序
	 */
	public List<T> getTopRecord(final String hql,final int num) throws Exception;
	
	/**
	  * @Description 获取对象数据列表
	  * @author liuxd
	  * @date 2015-8-1 下午5:02:33
	  * @version V1.0
	 */
	public List<T> findAll()throws Exception;
	/**
	  * @Description 分页获取对象数据列表
	  * @author liuxd
	  * @date 2015-8-1 下午5:12:37
	  * @version V1.0
	 */
	public Page<T> findPage(final Page<T> page)throws Exception;
	/**
	  * @Description hql方式检索对象数据列表
	  * @author liuxd
	  * @date 2015-8-1 下午5:07:47
	  * @version V1.0
	 */
	public List<T> findAll(String hql)throws Exception;
	/**
	  * @Description hql方式 组合
	  * @author liuxd
	  * @date 2015-8-1 下午5:07:47
	  * @version V1.0
	 */
	public List<Object[]> findObjectAll(String hql)throws Exception;
	
	/**
	  * @Description hql方式分页获取对象数据列表 
	  * @author liuxd
	  * @date 2015-8-1 下午5:11:18
	  * @version V1.0
	 */
	public Page<T> findPage(final Page<T> page, final String hql)throws Exception;
	
	/**
	  * @Description jdbc方式执行SQL语句
	  * @author liuxd
	  * @date 2015-8-1 下午5:03:35
	  * @version V1.0
	 */
	public void dbExecuSql(String sql,Map<Integer,Object> value) throws Exception ;
	/**
	  * @Description jdbc方式获取SQL数据列表
	  * @author liuxd
	  * @date 2015-8-1 下午5:04:07
	  * @version V1.0
	 */
	public List<Object[]> dbFindList(String sql,Map<Integer,Object> value) throws Exception;
	
	/**
	  * @Description jdbc方式获取SQL行数
	  * @author liuxd
	  * @date 2015-8-2 上午10:01:55
	  * @version V1.0
	 */
	public long countSqlResult(final String sql,Map<Integer,Object> value)throws Exception ;
	/**
	  * @Description jdbc方式获取SQL分页数据列表
	  * @author liuxd
	  * @date 2015-8-1 下午5:20:31
	  * @version V1.0
	 */
	public Page<Object[]> dbFindPageList(final Page<Object[]> page, String sql,Map<Integer,Object> value) throws Exception;
	/**
	  * @Description jdbc方式批量执行SQL语句
	  * @author liuxd
	  * @date 2015-8-1 下午5:04:23
	  * @version V1.0
	 */
	public void dbBeatchSql(List<String> sqls) throws Exception;
	/**
	* @Description 根据父ID迭代获取子ID和自身ID 
	* @return [[id,parentid],[id,parentid],....]
	* @author liuxd
	* @date 2018-7-31 下午2:44:37
	* @version V1.0
	 */
	public List<Object[]> findChildsByParentId(String tableName,String parentId,String condition)throws Exception;
	
	/**
	 * 
	* @Description 根据子ID迭代获取父ID和自身ID
	* @return [[id,parentid],[id,parentid],....]
	* @author liuxd
	* @date 2018-7-31 下午2:44:37
	* @version V1.0
	 */
	public List<Object[]> findParentByChildId(String tableName,String childId,String condition)throws Exception;
}
