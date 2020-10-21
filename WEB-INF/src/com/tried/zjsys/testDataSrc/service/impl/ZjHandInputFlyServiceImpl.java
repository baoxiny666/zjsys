package com.tried.zjsys.testDataSrc.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.tried.base.service.impl.BaseServiceImpl;
import com.tried.zjsys.testDataSrc.model.YinguangSrc;
import com.tried.zjsys.testDataSrc.model.ZjHandInputFly;
import com.tried.zjsys.testDataSrc.service.ZjHandInputFlyService;
/**
 * @Description - 服务接口实现
 * @author sunlunan
 * @date 2020-07-08 14:56:17
 * @version V1.0
 */
@Service
public class ZjHandInputFlyServiceImpl extends BaseServiceImpl<ZjHandInputFly> implements ZjHandInputFlyService {

	@Override
	public void del(String[] ids) throws Exception {

		for (String id : ids) {
			ZjHandInputFly obj = this.getById(id);
			if ("原始数据".equals(obj.getDataStatus())) {
				obj.setDataStatus("作废数据");
				this.update(obj);
			}
		}

		
	}

	@Override
	public void recovery(String[] ids) throws Exception {		
		for (String id : ids) {
			ZjHandInputFly obj = this.getById(id);
			if ("作废数据".equals(obj.getDataStatus())) {
				obj.setDataStatus("原始数据");
				this.update(obj);
			}
		}
}

	@Override
	public void flySrcData(String[] ids, ZjHandInputFly model) throws Exception {
		StringBuilder idstr=new StringBuilder("'-1'");
	      for(String id:ids){
	    	  idstr.append(",'"+id+"'");	
	      } 
		 List<ZjHandInputFly> zjlist=this.findAll(" from ZjHandInputFly where id in ("+idstr.toString()+")");
		 for(ZjHandInputFly obj:zjlist){		 
			 obj.setDataStatus("已发送");
			 }
		
	}

}
