package com.tried.zjsys.basics.thread;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tried.common.ConfigUtils;
import com.tried.zjsys.basics.model.DataCircle;

public class ThreadStaticVariable {
 public static Map<String,DataCircle> DataCircleMap=new HashMap<String,DataCircle>();
 public static int invialLength=2;
 public static Map<String,Map<String,Set<String>>> sampleNumMap=new HashMap<String,Map<String,Set<String>>>();

 //保证当天数据唯一
public static boolean CheckKeyTime(String deviceNum,String keyValue){
	synchronized (sampleNumMap) {	
	if(!sampleNumMap.containsKey(deviceNum)){
		Set<String> set=new HashSet<String>();set.add(keyValue);
		Map<String,Set<String>> map=new HashMap<String, Set<String>>();
		map.put(ConfigUtils.dataToSimpleString(new Date()), set);
		sampleNumMap.put(deviceNum, map);
		return true;
	}else{
		String lastDate=ConfigUtils.getDate(new Date(), -1, "日");
		if(sampleNumMap.get(deviceNum).containsKey(lastDate)){
			sampleNumMap.get(deviceNum).remove(lastDate);
		}
		Map<String,Set<String>> map=sampleNumMap.get(deviceNum);
		if(map.containsKey(ConfigUtils.dataToSimpleString(new Date()))){
			Set<String> set =map.get(ConfigUtils.dataToSimpleString(new Date()));
			if(set.contains(keyValue)){
				return false;
			}else{
				map.get(ConfigUtils.dataToSimpleString(new Date())).add(keyValue);
				sampleNumMap.put(deviceNum,map);
				return true;
			}
		}else{
			Set<String> set=new HashSet<String>();set.add(keyValue);
			map.put(ConfigUtils.dataToSimpleString(new Date()),set);
			sampleNumMap.put(deviceNum,map);
			return true;
		}
		
	}
	}
}
 
}
