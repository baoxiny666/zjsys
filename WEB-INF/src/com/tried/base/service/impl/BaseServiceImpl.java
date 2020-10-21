package com.tried.base.service.impl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.tried.base.service.BaseService;
import com.tried.common.Page;
import com.tried.system.model.SystemDelHistory;

/**
 * @Description
 * @author liuxd
 * @date 2015-8-2 上午8:51:07
 * @version V1.0
 */
@Transactional
public abstract class BaseServiceImpl<T> implements BaseService<T> {
	@Resource
	private SessionFactory sessionFactory;
	protected Class<T> object;

	protected Session getSession() throws Exception {
		return sessionFactory.getCurrentSession();
	}

	/**
	 *  实体对象托管
		 * @Description 
		 * @author liuxd
		 * @date 2019-3-15 上午10:36:34
		 * @version V1.0
	 */
	public void evict(T entity)throws Exception{
		getSession().evict(entity);
	}
	
	public BaseServiceImpl() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.object = (Class) pt.getActualTypeArguments()[0];
	}

	@Override
	public void add(T entity) throws Exception {
		getSession().save(entity);
	}

	@Override
	public void delete(String id) throws Exception {
		delete(getById(id));
	}
	@Override
	public void beachDelete(String[] idArray)throws Exception{
		for(String id:idArray){
			delete(getById(id));
		}
	}
	
	
	
	public void delete(T entity)throws Exception{
		SystemDelHistory his=new SystemDelHistory();
		his.setDelContext(JSONObject.fromObject(entity).toString());
		his.setCname(entity.getClass().getSimpleName());
		his.setRecordTime(new Date());	
		Method[] methods=entity.getClass().getDeclaredMethods();
		for(Method m:methods){
			if("getRecordUser".equals(m.getName())){
				String userId=(String) m.invoke(entity);
				his.setRecordUser(userId);
				break;
			}
		}	
 
		getSession().save(his);
		getSession().delete(entity);
	}
	
	@Override
	public void update(T entity) throws Exception {
		getSession().update(entity);
	}

	@Override
	public T getById(String id) throws Exception {
		return (T) getSession().get(object, id);
	}

	
	@Override
	public T getFirstRecordByField(String hql) throws Exception {
		Query q = createQuery(hql);	
		q.setFirstResult(0);
		q.setMaxResults(1);
		List<T> result =q.list();
		return result.size()>0?(T)result.get(0):null;  	
	}
	
	public List<T> getTopRecord(final String hql,final int num) throws Exception{
		Query q = createQuery(hql);	
		q.setFirstResult(0);
		q.setMaxResults(num);
		List<T> result =q.list();
		return result;  
	}
	
	@Override
	public List<T> findAll() throws Exception {
		return getSession().createQuery("FROM " + object.getSimpleName()).list();
	}

	@Override
	public Page<T> findPage(Page<T> page) throws Exception {
		Query q = createQuery("FROM " + object.getSimpleName());
		page.setTotal(countHqlResult("FROM " + object.getSimpleName()));
		setPageParameterToQuery(q, page);
		List<T> result = q.list();
		page.setData(result);
		return page;
	}

	@Override
	public List<T> findAll(String hql) throws Exception {
		return getSession().createQuery(hql).list();
	}
	public List<Object[]> findObjectAll(String hql)throws Exception{
		return getSession().createQuery(hql).list();
	}
	
	@Override
	public Page<T> findPage(Page<T> page, String hql) throws Exception {
		Query q = createQuery(hql);
		page.setTotal(countHqlResult(hql));
		setPageParameterToQuery(q, page);
		List<T> result = q.list();
		page.setData(result);
		return page;
	}

	@Override
	public void dbExecuSql(String sql, Map<Integer, Object> value) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Connection conn = session.connection();
		PreparedStatement preStat = conn.prepareStatement(sql);
		if (null != value) {
			Iterator<Integer> it = value.keySet().iterator();
			while (it.hasNext()) {
				Integer key = it.next();
				Object val = value.get(key);
				preStat.setObject(key, val);
			}
		}
		preStat.execute();
		preStat.close();
	}

	@Override
	public List<Object[]> dbFindList(String sql, Map<Integer, Object> value) throws Exception {
		List<Object[]> resultLIst = new ArrayList<Object[]>();
		Session session = this.sessionFactory.getCurrentSession();
		Connection conn = session.connection();
		PreparedStatement preStat = conn.prepareStatement(sql);
		if (null != value) {
			Iterator<Integer> it = value.keySet().iterator();
			while (it.hasNext()) {
				Integer key = it.next();
				Object val = value.get(key);
				preStat.setObject(key, val);
			}
		}
		ResultSet result = preStat.executeQuery();
		int count = result.getMetaData().getColumnCount();
		while (result.next()) {
			Object[] array = new Object[count];
			for (int i = 0; i < count; i++) {
				array[i] = result.getString(i + 1);
			}
			resultLIst.add(array);
		}
		result.close();
		preStat.close();
		return resultLIst;
	}

	@Override
	public Page<Object[]> dbFindPageList(Page<Object[]> page, String sql, Map<Integer, Object> value) throws Exception {

		page.setTotal(countSqlResult(sql, value));
		List<Object[]> resultLIst = new ArrayList<Object[]>();
		Session session = this.sessionFactory.getCurrentSession();
		Connection conn = session.connection();
		PreparedStatement preStat = conn.prepareStatement(sql + " OFFSET " + (page.getPageNum() - 1) * page.getRows() + " ROWS FETCH NEXT "
				+ page.getRows() + " ROWS ONLY");
 		if (value != null) {
			Iterator<Integer> it = value.keySet().iterator();
			while (it.hasNext()) {
				Integer key = it.next();
				Object val = value.get(key);
				preStat.setObject(key, val);
			}
		}
		ResultSet result = preStat.executeQuery();
		int count = result.getMetaData().getColumnCount();
		while (result.next()) {
			Object[] array = new Object[count];
			for (int i = 0; i < count; i++) {
				array[i] = result.getString(i + 1);
			}
			resultLIst.add(array);
		}
		page.setData(resultLIst);
		result.close();
		preStat.close();
		return page;
	}

	@Override
	public long countSqlResult(final String sql, Map<Integer, Object> value) throws Exception {
		long count = 0l;
		Session session = this.sessionFactory.getCurrentSession();
		Connection conn = session.connection();
		PreparedStatement preStat = conn.prepareStatement(prepareCountHql(sql));
		if (value != null) {
			Iterator<Integer> it = value.keySet().iterator();
			while (it.hasNext()) {
				Integer key = it.next();
				Object val = value.get(key);
				preStat.setObject(key, val);
			}
		}
		ResultSet result = preStat.executeQuery();

		while (result.next()) {
			count = result.getInt(1);
		}
		result.close();
		preStat.close();
		return count;
	}

	@Override
	public void dbBeatchSql(List<String> sqls) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Connection conn = session.connection();
		Statement stat = conn.createStatement();
		for (int i = 0; i < sqls.size(); i++) {
			stat.addBatch(sqls.get(i));
		}
		stat.executeBatch();
		stat.close();
	}

	public Query createQuery(final String queryString) throws Exception {
		Query query = getSession().createQuery(queryString);
		return query;
	}

	protected long countHqlResult(final String hql) throws Exception {
		String countHql = prepareCountHql(hql);
		Long count = findUnique(countHql);
		return count;
	}

	private String prepareCountHql(String orgHql) {
		String fromHql = orgHql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");
		String countHql = "select count(*) " + fromHql;
		return countHql;
	}

	public <T> T findUnique(final String hql) throws Exception {
		return (T) createQuery(hql).uniqueResult();
	}

	protected Query setPageParameterToQuery(final Query q, final Page<T> page) {
		q.setFirstResult((page.getPageNum() - 1) * page.getRows());
		q.setMaxResults(page.getRows());
		return q;
	}
	/**
	 * 根据父ID迭代获取子ID和自身ID
	* @Description 
	* @author liuxd
	* @date 2018-7-31 下午2:44:37
	* @version V1.0
	 */
	public List<Object[]> findChildsByParentId(String tableName,String parentId,String condition)throws Exception{
		if(condition==null||condition.isEmpty()){
			condition="";
		}
		String sql="WITH Tree AS ( "
				   +" SELECT id,parentId FROM "+tableName+" p WHERE p.id = '"+parentId+"'"
				   +"  UNION ALL"
				   +"  SELECT c.id ,c.parentId FROM "+tableName+" c "
				   +" INNER JOIN Tree t ON c.parentId = t.id "+condition
				   +" ) SELECT * FROM Tree ";
		return this.dbFindList(sql, null);
	};
	
	/**
	 * 根据子ID迭代获取父ID和自身ID
	* @Description 
	* @author liuxd
	* @date 2018-7-31 下午2:44:37
	* @version V1.0
	 */
	public List<Object[]> findParentByChildId(String tableName,String childId,String condition)throws Exception{
		String sql="WITH Tree AS "
				+" ( SELECT id,parentId FROM tried_system_department c WHERE c.id ='8aa4847e64da57310164da5ba9420005'  " 
				+"  UNION ALL "
				+" SELECT  p.id,p.parentId FROM tried_system_department p "
				+" INNER JOIN Tree t ON p.id = t.parentId "+condition 
				+" ) SELECT * FROM Tree ";
		return this.dbFindList(sql, null);
	};
}
