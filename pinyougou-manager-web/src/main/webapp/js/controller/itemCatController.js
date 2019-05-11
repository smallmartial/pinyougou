 //控制层 
app.controller('itemCatController' ,function($scope,$controller,itemCatService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemCatService.update( $scope.entity ); //修改  
		}else{
            $scope.entity.parentId=$scope.parentId;//赋予上级ID
			serviceObject=itemCatService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询
                   $scope.findByParentId($scope.parentId);//重新加载
                   // $scope.loadGroupByLevel(lastLevel());

                    //$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除
    //todo 删除未完成
	$scope.dele=function(){			
		//获取选中的复选框			
		itemCatService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
                    $scope.findByParentId($scope.parentId);//重新加载
                    console.log($scope.parentId);
                   // alert(findByParentId);
                    // $scope.loadGroupByLevel(grade);
                   // alert("删除完成,存在子分类的分类不会被删除.");
                   $scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    $scope.parentId=0;//上级ID

    //根据上级ID显示下级列表
    $scope.findByParentId=function(parentId){
        $scope.parentId=parentId;//记住上级ID
        itemCatService.findByParentId(parentId).success(
            function(response){
                $scope.list=response;
            }
        );
    }


    $scope.grade=1;//默认为1级
    //设置级别
    $scope.setGrade=function(value){
        $scope.grade=value;
    }
    //读取列表
    $scope.selectList=function(p_entity){
        if($scope.grade==1){//如果为1级
            $scope.entity_1=null;
            $scope.entity_2=null;
        }
        if($scope.grade==2){//如果为2级
            $scope.entity_1=p_entity;
            $scope.entity_2=null;
        }
        if($scope.grade==3){//如果为3级
            $scope.entity_2=p_entity;
        }
        $scope.findByParentId(p_entity.id);	//查询此级下级列表
    }

// //面包屑breadcrumb相关
//     //TODO click事件有BUG
//     const maxGroupLevelLength = 3;
//     $scope.getMaxGroupLevel = function () {
//         return maxGroupLevelLength;
//     }
//     function checkInRange(level) {
//         if(level < 0 || level >= maxGroupLevelLength){
//             throw "level越界:"+ level;
//         }
//     }
//     $scope.groupStack = [{id:0}];
//     $scope.level = 0;
//     $scope.loadGroupByLevel = function (level) {
//         checkInRange(level);
//         var group = $scope.groupStack[level];
//         itemCatService.findByParentId(group.id).success(function (response) {
//             $scope.list = response;
//         })
//     }
//     $scope.popGroup = function (level) {
//         checkInRange(level);
//         $scope.level = level;
//         for(var i = level+1 ; i < maxGroupLevelLength ; i++){
//             $scope.groupStack[i] = null;
//         }
//     }
//     $scope.getGroup = function (level) {
//         checkInRange(level);
//         return $scope.groupStack[level];
//     }
//     $scope.pushGroup = function (group) {
//         checkInRange($scope.level+1);
//         $scope.groupStack[$scope.level+1] = group;
//         $scope.level = $scope.level+1;
//     }
//     $scope.lastGroup = function () {
//         return $scope.groupStack[$scope.level];
//     }
//     $scope.lastLevel =function () {
//         return $scope.level;
//     }
//
//     $scope.refresh = function () {
//         $scope.findByParentId(0);
//     }

    // // select2相关
    //
    // $scope.typeTemplateConfig = {data: []};
    // $scope.loadTypeTemplateData = function () {
    //     typeTemplateService.findOptions().success(function (response) {
    //         $scope.typeTemplateConfig.data = response;
    //     })
    // }


});	
