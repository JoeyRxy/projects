<%@page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" language="java" %>

<!DOCTYPE html>
<html lang="zxx">

<head>
	<title>价格</title>
	<!-- Meta tag Keywords -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta charset="UTF-8" />
	<meta name="keywords" content="" />
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
								<li><a href="pricing.jsp" class="active">价格</a></li>
								<li>
									<!-- First Tier Drop Down -->
									<label for="drop-2" class="toggle toogle-2">更多 <span class="fa fa-angle-down"
											aria-hidden="true"></span>
									</label>
									<a href="#">更多 <span class="fa fa-angle-down" aria-hidden="true"></span></a>
									<input type="checkbox" id="drop-2" />
									<ul>
										<li><a href="index.jsp" class="drop-text">服务</a></li>
										<li><a href="faq.jsp" class="drop-text">疑问</a></li>
										<li><a href="signin.jsp" class="drop-text">登录</a></li>
										<li><a href="index.jsp" class="drop-text">Statistics</a></li>
										<li><a href="about.jsp" class="drop-text">Why Choose Us?</a></li>
										<li><a href="about.jsp" class="drop-text">Our Team</a></li>
										<li><a href="index.jsp" class="drop-text">Partners</a></li>
									</ul>
								</li>
							</ul>
						</nav>
					</div>
					<!-- //nav -->
					<div class="d-flex mt-lg-1 mt-sm-2 mt-3 justify-content-center">
						<nav>
							<ul>
								<%
									String name= (String) session.getAttribute("uname");
									if(name!=null){
										out.print("<li><a href=\"space.jsp\">"+name+"</a></li>\n");
									}
									else{
										out.print("<li><a href=\"signup.jsp\">注册</a></li>\n" +
												"<li><a href=\"signin.jsp\" class=\"drop-text\">登录</a></li>");
									}
								%>
							</ul>
						</nav>
					</div>
				</div>
			</div>
		</header>
		<!-- //header -->

		<!-- banner -->
		<div class="banner_w3lspvt-2">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="index.jsp" class="font-weight-bold">主页</a></li>
				<li class="breadcrumb-item" aria-current="page">价格</li>
			</ol>
		</div>
		<!-- //banner -->
	</div>
	<!-- //main banner -->

	<!-- price -->
	<div class="price-sec py-5">
		<div class="container py-xl-5 py-lg-3">
			<div class="inner_sec_info_w3_info mt-3">
				<div class="row price-grid-main">
					<div class="col-lg-3 col-sm-6 price-info">
						<div class="prices p-4">
							<div class="prices-top">
								<h3 class="text-center text-wh rounded-circle">$30</h3>
							</div>
							<div class="prices-bottom text-center">
								<div class="prices-h border-bottom p-4">
									<h4>Standard</h4>
								</div>
								<ul class="mt-4">
									<li>Community Access</li>
									<li>Annual Reports</li>
									<li>Free Support</li>
									<li>Expert Reviews</li>
								</ul>
								<a href="#" class="btn button-style mt-4">Purchase</a>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6  price-info price-mkres-2">
						<div class="prices p-4 active">
							<div class="prices-top">
								<h3 class="text-center text-wh rounded-circle">$80</h3>
							</div>
							<div class="prices-bottom text-center">
								<div class="prices-h border-bottom p-4">
									<h4>Premium</h4>
								</div>
								<ul class="mt-4">
									<li>Limitless Concepts</li>
									<li>Annual Reports</li>
									<li>Free Support</li>
									<li>Expert Reviews</li>
								</ul>
								<a href="#" class="btn button-style mt-4">Purchase</a>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6  price-info price-mkres">
						<div class="prices p-4">
							<div class="prices-top">
								<h3 class="text-center text-wh rounded-circle">$60</h3>
							</div>
							<div class="prices-bottom text-center">
								<div class="prices-h border-bottom p-4">
									<h4>Golden</h4>
								</div>
								<ul class="mt-4">
									<li>Community Access</li>
									<li>Annual Reports</li>
									<li>Free Support</li>
									<li>Expert Reviews</li>
								</ul>
								<a href="#" class="btn button-style mt-4">Purchase</a>
							</div>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6  price-info price-mkres">
						<div class="prices p-4 active">
							<div class="prices-top">
								<h3 class="text-center text-wh rounded-circle">$30</h3>
							</div>
							<div class="prices-bottom text-center">
								<div class="prices-h border-bottom p-4">
									<h4>Ultimate</h4>
								</div>
								<ul class="mt-4">
									<li>Limitless Concepts</li>
									<li>Annual Reports</li>
									<li>Free Support</li>
									<li>Expert Reviews</li>
								</ul>
								<a href="#" class="btn button-style mt-4">Purchase</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- //price -->

	<!-- footer -->
	<footer class="bg-li py-5">
		<div class="container py-xl-5 py-lg-3">
			<!-- subscribe -->
			<div class="subscribe mx-auto">
				<div class="icon-effect-w3">
					<span class="fa fa-envelope"></span>
				</div>
				<h2 class="tittle text-center font-weight-bold">Stay Updated!</h2>
				<p class="sub-tittle text-center mt-3 mb-sm-5 mb-4">Sed do eiusmod tempor incididunt ut labore et dolore
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