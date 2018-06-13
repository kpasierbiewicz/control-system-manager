<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml"
 xmlns:th="http://www.thymeleaf.org">
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
 <title>Sign up</title>
 <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
 <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.min.css">
</head>
<body>
 <div class="container">
     <form class="form-horizontal" role="form" method="POST" action="signup" object="${user}">
      <div class="row">
             <div class="col-md-3"></div>
             <div class="col-md-6">
             <c:if test = "${msg}">
              <div class="alert alert-success" role="alert">
              		Success
     			</div>
     			</c:if>
             </div>
         </div>
         <div class="row">
             <div class="col-md-3"></div>
             <div class="col-md-6">
                 <h2>Register New User</h2>
                 <hr>
             </div>
         </div>
         <div class="row">
             <div class="col-md-3 field-label-responsive">
                 <label for="name">First Name</label>
             </div>
             <div class="col-md-6">
                 <div class="form-group">
                     <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                         <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-user"></i></div>
                         <input type="text" name="firstname" class="form-control" id="firstname"
                                placeholder="First Name" required autofocus>
                     </div>
                 </div>
             </div>
         </div>
         <div class="row">
             <div class="col-md-3 field-label-responsive">
                 <label for="name">Last Name</label>
             </div>
             <div class="col-md-6">
                 <div class="form-group">
                     <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                         <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-user"></i></div>
                         <input type="text" name="lastname" class="form-control" id="lastname"
                                placeholder="Last name" required autofocus>
                     </div>
                 </div>
             </div>
         </div>
         <div class="row">
             <div class="col-md-3 field-label-responsive">
                 <label for="email">Email</label>
             </div>
             <div class="col-md-6">
                 <div class="form-group">
                     <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                         <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-at"></i></div>
                         <input type="text" name="email" class="form-control" id="email"
                                placeholder="username@jackrutorial.com" required autofocus>
                     </div>
                 </div>
             </div>
   
         </div>
         <div class="row">
             <div class="col-md-3 field-label-responsive">
                 <label for="password">Password</label>
             </div>
             <div class="col-md-6">
                 <div class="form-group has-danger">
                     <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                         <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-key"></i></div>
                         <input type="password" name="password" class="form-control" id="password"
                                placeholder="Password" required>
                     </div>
                 </div>
             </div>
             
         </div>
         <div class="row">
             <div class="col-md-3"></div>
             <div class="col-md-6">
                 <button type="submit" class="btn btn-success"><i class="fa fa-user-plus"></i> Register</button>
                 <div class="margin-top20 text-center"><a href="login">Login in</a></div>
             </div>
         </div>
     </form>
 </div>
</body>
</html>