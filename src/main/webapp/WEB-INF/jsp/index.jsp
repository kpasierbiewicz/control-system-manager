<!DOCTYPE HTML>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

 <html xmlns="http://www.w3.org/1999/xhtml" 
    xmlns:th="http://www.thymeleaf.org" 
    xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3"
    xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Pragma" content="no-cache"> 
    <meta http-equiv="Cache-Control" content="no-cache"> 
    <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
    
    <title>Manager | Home</title>
    
    <link href="static/css/bootstrap.min.css" rel="stylesheet"/>
     <link href="static/css/style.css" rel="stylesheet"/>
    <script src="static/js/jquery-1.11.1.min.js"></script>
    <script src="static/js/three.js"></script>
    <!--[if lt IE 9]>
		<script src="static/js/html5shiv.min.js"></script>
		<script src="static/js/respond.min.js"></script>
	<![endif]-->
</head>
<body>

	<div role="navigation">
		<div class="navbar navbar-inverse">
			<a href="/" class="navbar-brand">Manager</a>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="new-task">New Task</a></li>
					<li><a href="all-tasks">All Tasks</a></li>
					<li><a href="showFiles.html" target="_blank">Show Files</a></li>
					<li><a href="uploadFileManager.html" target="_blank">Upload Files</a></li>
					<li><a href="chat.html" target="_blank">Chat</a></li>
					<li><a href="createRaport">Create Raport</a></li>
				</ul>
			</div>
		</div>
	</div>
	
	<c:choose>
		<c:when test="${mode == 'MODE_HOME'}">
			<div class="container" id="homeDiv">
				<div class="jumbotron text-center">
					<h1>Welcome to System</h1>
				</div>
			</div>
		</c:when>
		<c:when test="${mode == 'MODE_TASKS'}">
			<div class="container text-center" id="tasksDiv">
				<h3>My Tasks</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered text-left">
						<thead>
							<tr>
								<th>Id</th>
								<th>Name</th>
								<th>Description</th>
								<th>Date Created</th>
								<th>Finished</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="task" items="${tasks}">
								<tr>
									<td>${task.id}</td>
									<td>${task.name}</td>
									<td>${task.description}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${task.dateCreated}"/></td>
									<td>${task.finished}</td>
									<td><a href="update-task?id=${task.id}"><span class="glyphicon glyphicon-pencil"></span></a></td>
									<td><a href="delete-task?id=${task.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>
		<c:when test="${mode == 'MODE_NEW' || mode == 'MODE_UPDATE'}">
			<div class="container text-center">
				<h3>Manage Task</h3>
				<hr>
				<form class="form-horizontal" method="POST" action="save-task">
					<input type="hidden" name="id" value="${task.id}"/>
					<div class="form-group">
						<label class="control-label col-md-3">Name</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="name" value="${task.name}"/>
						</div>				
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Description</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="description" value="${task.description}"/>
						</div>				
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Finished</label>
						<div class="col-md-7">
							<input type="radio" class="col-sm-1" name="finished" value="true"/>
							<div class="col-sm-1">Yes</div>
							<input type="radio" class="col-sm-1" name="finished" value="false" checked/>
							<div class="col-sm-1">No</div>
						</div>				
					</div>		
					<div class="form-group">
						<input type="submit" class="btn btn-primary" value="Save"/>
					</div>
				</form>
			</div>
		</c:when>
		<c:when test="${mode == 'MODE_FILES'}">
			<div class="container text-center" id="tasksDiv">
				<h3>My Files</h3>
				<hr>
				<div class="table-responsive">
					<ul>
							<c:forEach var="file" items="${files}">
								<li><a href="/uploads/${file}" download>${file}</a> ==> <a href="generateModel/${file}" target="_blank"><b>Show information</b></a></li>
							</c:forEach>
					</ul>
				</div>
				<select>
				<c:forEach var="file" items="${files}">
								<option value="${file}">${file}</option>
				</c:forEach>
				</select>
				<script type="text/javascript">
				
				var fileName;
			    $('select').click(function(){
			        console.log($(this).val());
			        fileName = $(this).val();
			       
					
					$.ajax({
						  method: 'GET',
						  url: "generateModel/" + fileName,
						  contentType: "application/json; charset=utf-8",
						  dataType: "json",
						  success: function(data) {
							  
							  var three = THREE;

							  var scene = new three.Scene();
							  var camera = new three.PerspectiveCamera(50, window.innerWidth/window.innerHeight, 0.1, 1000);

							  var renderer = new three.WebGLRenderer();
							  renderer.setSize(window.innerWidth, window.innerHeight);

							  document.body.appendChild(renderer.domElement);

							  var geometry = new three.CubeGeometry(0.003, 0.003, 0.003);
							  var material = new THREE.MeshBasicMaterial( { color: 0xffff00 } );
							  
							   for (var i = 0; i < 1404; i++) { 
							
								if (data.temperature[i] <= 250) material = new THREE.MeshBasicMaterial( { color: 0x0000ff } );
								else if (data.temperature[i] > 250 && data.temperature[i] <= 500) material = new THREE.MeshBasicMaterial( { color: 0xffff00 } );
								else if (data.temperature[i] > 500 && data.temperature[i] <= 990) material = new THREE.MeshBasicMaterial( { color: 0xffa500 } );
								else if (data.temperature[i] > 990) material = new THREE.MeshBasicMaterial( { color: 0xff0000 } );
							   	var cube = new three.Mesh(geometry, material);
							    cube.position.x = data.positionX[i];
							    cube.position.y = data.positionY[i];
							    cube.position.z = data.positionZ[i];
							    scene.add(cube);
							    }

							  camera.position.z = 0.2;

							  /* */
							  var isDragging = false;
							  var previousMousePosition = {
							      x: 0,
							      y: 0
							  };
							  $(renderer.domElement).on('mousedown', function(e) {
							      isDragging = true;
							  })
							  .on('mousemove', function(e) {
							      //console.log(e);
							      var deltaMove = {
							          x: e.offsetX-previousMousePosition.x,
							          y: e.offsetY-previousMousePosition.y
							      };

							      if(isDragging) {
							              
							          var deltaRotationQuaternion = new three.Quaternion()
							              .setFromEuler(new three.Euler(
							                  toRadians(deltaMove.y * 1),
							                  toRadians(deltaMove.x * 1),
							                  0,
							                  'XYZ'
							              ));
							          
							          scene.quaternion.multiplyQuaternions(deltaRotationQuaternion, scene.quaternion);
							      }
							      
							      previousMousePosition = {
							          x: e.offsetX,
							          y: e.offsetY
							      };
							  });
							  /* */

							  $(document).on('mouseup', function(e) {
							      isDragging = false;
							  });



							  // shim layer with setTimeout fallback
							  window.requestAnimFrame = (function(){
							      return  window.requestAnimationFrame ||
							          window.webkitRequestAnimationFrame ||
							          window.mozRequestAnimationFrame ||
							          function(callback) {
							              window.setTimeout(callback, 1000 / 60);
							          };
							  })();

							  var lastFrameTime = new Date().getTime() / 1000;
							  var totalGameTime = 0;
							  function update(dt, t) {

							      
							      setTimeout(function() {
							          var currTime = new Date().getTime() / 1000;
							          var dt = currTime - (lastFrameTime || currTime);
							          totalGameTime += dt;
							          
							          update(dt, totalGameTime);
							      
							          lastFrameTime = currTime;
							      }, 0);
							  }


							  function render() {
							      renderer.render(scene, camera);
							      
							      
							      requestAnimFrame(render);
							  }

							  render();
							  update(0, totalGameTime);


							  function toRadians(angle) {
							  	return angle * (Math.PI / 180);
							  }

							  function toDegrees(angle) {
							  	return angle * (180 / Math.PI);
							  }


						  }
						});
			    });
			   
				</script>
			</div>
		</c:when>		
	</c:choose>

	<script src="static/js/jquery-1.11.1.min.js"></script>    
    <script src="static/js/bootstrap.min.js"></script>
</body>
</html>