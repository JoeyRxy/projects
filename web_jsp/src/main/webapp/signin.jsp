<%@page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html>
<html lang="zxx">

<head>
	<title>注册</title>
	<!-- Meta tag Keywords -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta charset="UTF-8" />
	<meta name="keywords" content="" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script>
		addEventListener("load", function () {
			setTimeout(hideURLbar, 0);
		}, false);

		function hideURLbar() {
			window.scrollTo(0, 1);
		}
	</script>
	<!-- //Meta tag Keywords -->

	<!-- Custom-Files -->
	<link rel="stylesheet" href="css/bootstrap.css">
	<!-- Bootstrap-Core-CSS -->
	<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
	<!-- Style-CSS -->
	<link href="css/font-awesome.min.css" rel="stylesheet">
	<!-- Font-Awesome-Icons-CSS -->
	<!-- //Custom-Files -->

	<!-- Web-Fonts -->
	<link
		href="http://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&amp;subset=devanagari,latin-ext"
		rel="stylesheet">
	<!-- //Web-Fonts -->
</head>

<body>
	<!-- main banner -->
	<div class="main-top" id="home">
		<!-- header -->
		<header>
			<div class="container-fluid">
				<div class="header d-lg-flex justify-content-between align-items-center py-3 px-sm-3">
					<!-- logo -->
					<div id="logo">
						<h1><a href="index.jsp"><span class="fa fa-linode mr-2"></span>Rxy</a></h1>
					</div>
					<!-- //logo -->
					<!-- nav -->
					<div class="nav_w3ls">
						<nav>
							<label for="drop" class="toggle">Menu</label>
							<input type="checkbox" id="drop" />
							<ul class="menu">
								<li><a href="index.jsp">主页</a></li>
								<li><a href="about.jsp">关于我们</a></li>
								<li><a href="pricing.jsp">价格</a></li>
								<li>
									<!-- First Tier Drop Down -->
									<label for="drop-2" class="toggle toogle-2">更多 <span class="fa fa-angle-down"
											aria-hidden="true"></span>
									</label>
									<a href="#">更多 <span class="fa fa-angle-down" aria-hidden="true"></span></a>
									<input type="checkbox" id="drop-2" />
									<ul>
										<li><a href="faq.jsp" class="drop-text">疑问</a></li>
										<li><a href="signin.jsp" class="drop-text">登录</a></li>
										<li><a href="index.jsp" class="drop-text">Statistics</a></li>
										<li><a href="about.jsp" class="drop-text">Why Choose Us?</a></li>
										<li><a href="about.jsp" class="drop-text">Our Team</a></li>
										<li><a href="index.jsp" class="drop-text">Partners</a></li>
									</ul>
								</li>
								<li><a href="signup.jsp" class="active">注册</a></li>
							</ul>
						</nav>
					</div>
					<!-- //nav -->
					<div class="d-flex mt-lg-1 mt-sm-2 mt-3 justify-content-center">


					</div>
				</div>
			</div>
		</header>
		<!-- //header -->

		<!-- banner -->
		<div class="banner_w3lspvt-2">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="index.jsp" class="font-weight-bold">主页</a></li>
				<li class="breadcrumb-item" aria-current="page">注册</li>
			</ol>
		</div>
		<!-- //banner -->
	</div>
	<!-- //main banner -->

	<!-- contact -->
	<div class="contact py-5" id="contact">
		<div class="container pb-xl-5 pb-lg-3">
			<div class="row">
				<div class="col-lg-6">
					<img src="images/b2.png" alt="image" class="img-fluid" />
				</div>
				<div class="col-lg-6 mt-lg-0 mt-5">
					<!-- contact form grid -->
					<div class="contact-top1">
						<form action="signin" method="post" class="contact-wthree-do" accept-charset="UTF-8">
							<nav class="form-group">
								<div class="row">
									<div class="col-md-6">

									</div>
								</div>
							</nav>
							<nav class="form-group">
								<div class="row">
									<div class="col-md-6">
										<label>
											姓名
										</label>
										<label>
											<input class="form-control" type="text" placeholder="某某某" name="uname"
												required="">
										</label>
									</div>
								</div>
							</nav>
							<nav class="form-group">
								<div class="row">
									<div class="col-md-6">
										<label>
											密码
										</label>
										<label>
											<input class="form-control" type="password" name="upwd"
												placeholder="********" required="">
										</label>
									</div>
								</div>
							</nav>
							<div class="row mt-3">
								<div class="col-md-6">
									<input type="submit" class="btn btn-cont-w3 btn-block mt-4" value="登录">
								</div>
							</div>
						</form>
					</div>
					<!-- //contact form grid ends here -->
				</div>
			</div>
		</div>
	</div>
	<!-- //contact-->


	<!-- footer -->
	<footer class="bg-li py-5">
		<div class="container py-xl-5 py-lg-3">
			<!-- subscribe -->
			<div class="subscribe mx-auto">
				<div class="icon-effect-w3">
					<span class="fa fa-envelope"></span>
				</div>
				<h2 class="tittle text-center font-weight-bold">Stay Updated!</h2>
				<p class="sub-tittle text-center mt-3 mb-sm-5 mb-4">Sed do eiusmod tempor incididunt ut labore et
					dolore
					magna
					aliqua. Ut enim ad minim veniam, quis nostrud exercitation</p>
				<form action="#" method="post" class="subscribe-wthree pt-2">
					<div class="d-flex subscribe-wthree-field">
						<input class="form-control" type="email" placeholder="Enter your email..." name="email"
							required="">
						<button class="btn form-control w-50" type="submit">Subscribe</button>
					</div>
				</form>
			</div>
			<!-- //subscribe -->
		</div>
	</footer>
	<!-- //footer -->
	<!-- copyright bottom -->
	<div class="copy-bottom bg-li py-4 border-top">
		<div class="container-fluid">
			<div class="d-md-flex px-md-3 position-relative text-center">
				<!-- footer social icons -->
				<div class="social-icons-footer mb-md-0 mb-3">
					<ul class="list-unstyled">
						<li>
							<a href="#">
								<span class="fa fa-facebook"></span>
							</a>
						</li>
						<li>
							<a href="#">
								<span class="fa fa-twitter"></span>
							</a>
						</li>
						<li>
							<a href="#">
								<span class="fa fa-google-plus"></span>
							</a>
						</li>
						<li>
							<a href="#">
								<span class="fa fa-instagram"></span>
							</a>
						</li>
					</ul>
				</div>
				<!-- //footer social icons -->

				<!-- move top icon -->
				<a href="#home" class="move-top text-center">
					<span class="fa fa-level-up" aria-hidden="true"></span>
				</a>
				<!-- //move top icon -->
			</div>
		</div>
	</div>
	<!-- //copyright bottom -->

</body>

</html>