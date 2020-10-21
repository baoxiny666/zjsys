/**  
    * gridId 列表id 通常为datagrid_
    * data 数据 [{},{},{}]
    * fieldName 标题替换列 
    * exceptField 不参与计数的列
    * count ：合计
    * avg：均值
    * max：最大值
    * min 最小值
    * zeroFlag 0是否参与计算
    */
   function func_analysis(gridId,data,fieldName,exceptField,count,avg,max,min,zeroFlag,fixed){
	if(fixed==undefined){
		   fixed=3;
	 }
   	var _columnAvg={};
   	var _columnMax={};
   	var _columnMin={};
   	var _columnAll={};
   	var _columnNum={};
   	var _columnFixed={};
   	
   	
   	$.each(data,function(i){
   		$.each(data[i],function(k,v){
   			if(exceptField.indexOf(k+";")==-1){
   				v = ""+v;
   				v=v.replace(/\s+/g,"");
   				 
   				if(zeroFlag){   //0 参与计算；
   						if(v==""||v==null){v="0";}
   						var _fixed=0;
   						if(v.indexOf(".")>=0){
   							_fixed = v.split(".")[1].length;
   		   				}
   						if(!(k in _columnAll) ){
   							_columnAll[k]=parseFloat(v);
   							_columnNum[k]=1;
   							_columnFixed[k]=_fixed;
   						}else{
   							_columnAll[k]=_columnAll[k]+parseFloat(v);
   							 _columnNum[k]=_columnNum[k]+1;
   						}
   						//最大值处理
   				    	if(max){
   				    	
   					    	if(!(k in _columnMax)){
   					    		_columnMax[k]=parseFloat(v);
   					    	}else{
   					    	     if(_columnMax[k]<parseFloat(v)){
   					    				_columnMax[k]=parseFloat(v);
   					    		}
   								 
   					    	}
   				    	}
   				    	
   				    	//最小值处理
   				    	if(min){
   					    	if(!(k in _columnMin)){
   					    		_columnMin[k]=parseFloat(v);
   					    	}else{
   					    		if(_columnMin[k]>parseFloat(v)){
   					    				_columnMin[k]=parseFloat(v);
   					    		}
   					    	}
   				    	}
   					 
   				}else{ //0 不参与计算；
   				    if(v!=""&&v!=null&&v!="0"&&v!="0.0"&&v!="0.00"){
   				    	var _fixed=0;
   				    	if(v.indexOf(".")>=0){
   							_fixed = v.split(".")[1].length;
   		   				}
   				    	//合计处理
   				    	if(!(k in _columnAll) ){
   							_columnAll[k]=parseFloat(v);
   							_columnNum[k]=1;
   							_columnFixed[k]=_fixed;
   						}else{
   						 if(parseFloat(v)!=0){
   							_columnAll[k]=_columnAll[k]+parseFloat(v);
   							 _columnNum[k]=_columnNum[k]+1;
   						  }
   						}
   				    	//最大值处理
   				    	if(max){
   				    	
   					    	if(!(k in _columnMax)){
   					    		_columnMax[k]=parseFloat(v);
   					    	}else{
   					    		if(parseFloat(v)!=0){
   					    			if(_columnMax[k]<parseFloat(v)){
   					    				_columnMax[k]=parseFloat(v);
   					    			}
   								}
   					    	}
   				    	}
   				    	
   				    	//最小值处理
   				    	if(min){
   					    	if(!(k in _columnMin)){
   					    		_columnMin[k]=parseFloat(v);
   					    	}else{
   					    		if(parseFloat(v)!=0){
   					    			if(_columnMin[k]>parseFloat(v)){
   					    				_columnMin[k]=parseFloat(v);
   					    			}
   								}
   					    	}
   				    	}
   				    	
   				    }
   				}
   			}
   		});	
   	})
   	
    var bgColor= "#1DA160";
   	var fortColor="#fff";
   	if(max){
   		$.each(_columnMax,function(k,v){
   			var _fixed = _columnFixed[k];
   			if(_fixed==undefined||_fixed==null){
   				_fixed = 2;
   			}
   			_columnMax[k]=parseFloat(parseFloat( _columnMax[k])).toFixed(_fixed);	
   		})
   		_columnMax[fieldName]="最大值";
   		$('#'+gridId).datagrid('appendRow', _columnMax);	
   		var _rowNum=$('#'+gridId).datagrid("getRowIndex",_columnMax);
   		$("tr[datagrid-row-index=\""+_rowNum+"\"]").css("background-color", bgColor);
   		$("tr[datagrid-row-index=\""+_rowNum+"\"]").css("color", fortColor);
   	}
   	if(min){
   		$.each(_columnMin,function(k,v){
   			var _fixed = _columnFixed[k];
   			if(_fixed==undefined||_fixed==null){
   				_fixed = 2;
   			}
   			_columnMin[k]=parseFloat(parseFloat(_columnMin[k])).toFixed(_fixed);	
   		})
   			_columnMin[fieldName]="最小值";
   		$('#'+gridId).datagrid('appendRow', _columnMin);	
   		var _rowNum=$('#'+gridId).datagrid("getRowIndex",_columnMin);
   		$("tr[datagrid-row-index=\""+_rowNum+"\"]").css("background-color", bgColor);
   		$("tr[datagrid-row-index=\""+_rowNum+"\"]").css("color", fortColor);
   	}
   	if(count){
   		$.each(_columnAll,function(k,v){
   			var _fixed = _columnFixed[k];
   		 
   			if(_fixed==undefined||_fixed==null){
   				_fixed = 2;
   			}
   			_columnAll[k]=parseFloat(_columnAll[k]).toFixed(_fixed);	
   		})
   		
   		_columnAll[fieldName]="合计";
   		$('#'+gridId).datagrid('appendRow', _columnAll);
   		var _rowNum=$('#'+gridId).datagrid("getRowIndex",_columnAll);
   		$("tr[datagrid-row-index=\""+_rowNum+"\"]").css("background-color", bgColor);
   		$("tr[datagrid-row-index=\""+_rowNum+"\"]").css("color", fortColor);
   	}
   	if(avg){
   		$.each(_columnAll,function(k,v){
   			var _fixed = _columnFixed[k];
   			if(_fixed==undefined||_fixed==null){
   				_fixed = 2;
   			}
   			_columnAvg[k]=parseFloat((v/_columnNum[k])).toFixed(_fixed);	
   		})
   		_columnAvg[fieldName]="均值";
   		$('#'+gridId).datagrid('appendRow', _columnAvg);
   		var _rowNum=$('#'+gridId).datagrid("getRowIndex",_columnAvg);
   		$("tr[datagrid-row-index=\""+_rowNum+"\"]").css("background-color", bgColor);
   		$("tr[datagrid-row-index=\""+_rowNum+"\"]").css("color", fortColor);
   		
   	}
   }