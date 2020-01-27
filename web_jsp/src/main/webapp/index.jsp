<%@page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html>
<html lang="zxx">

<head>
	<title>主页</title>
	<!-- Meta tag Keywords -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta charset="UTF-8" />
	<meta name="keywords" content="" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script src="js/script.js"></script>
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
								<li><a href="index.jsp" class="active">主页</a></li>
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
										<li><a href="#services" class="drop-text">服务</a></li>
										<li><a href="faq.jsp" class="drop-text">疑问</a></li>
										<li><a href="#stats" class="drop-text">Statistics</a></li>
										<li><a href="about.jsp" class="drop-text">Why Choose Us?</a></li>
										<li><a href="about.jsp" class="drop-text">Our Team</a></li>
										<li><a href="#partners" class="drop-text">Partners</a></li>
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
                                    if(name!=null) {
								%>
								<li>
									<a href="space.jsp"><%=name%> <span class="fa fa-angle-double-down"
											aria-hidden="true"></span></a>
									<ul>
										<li><a href="signoff" class="dropdown">登出</a></li>
									</ul>
								</li>
								<%
                                    } else {
								%>
								<li><a href="signup.jsp">注册</a></li>
								<li><a href="signin.jsp">登录</a></li>
								<%
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
		<div class="banner_w3lspvt position-relative">
			<div class="container">
				<div class="d-md-flex">
					<div class="w3ls_banner_txt">
						<h3 class="w3ls_pvt-title">Business <br><span>Startup</span></h3>
						<p class="text-sty-banner">Sed ut perspiciatis unde omnis iste natus doloremque
							laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi.</p>
						<a href="about.jsp" class="btn button-style mt-md-5 mt-4">Read More</a>
					</div>
					<div class="banner-img">
						<img src="images/banner.png" alt="" class="img-fluid">
					</div>
				</div>
			</div>
			<!-- animations effects -->
			<div class="icon-effects-w3l">
				<img src="images/shape1.png" alt="" class="img-fluid shape-wthree shape-w3-one">
				<img src="images/shape2.png" alt="" class="img-fluid shape-wthree shape-w3-two">
				<img src="images/shape3.png" alt="" class="img-fluid shape-wthree shape-w3-three">
				<img src="images/shape5.png" alt="" class="img-fluid shape-wthree shape-w3-four">
				<img src="images/shape4.png" alt="" class="img-fluid shape-wthree shape-w3-five">
				<img src="images/shape6.png" alt="" class="img-fluid shape-wthree shape-w3-six">
			</div>
			<!-- //animations effects -->
		</div>
		<!-- //banner -->
	</div>
	<!-- //main banner -->

	<!-- what we do section -->
	<div class="what bg-li py-5" id="what">
		<div class="container py-xl-5 py-lg-3">
			<div class="row about-bottom-w3l text-center mt-4">
				<div class="col-lg-4 about-grid">
					<div class="about-grid-main">
						<img src="images/img1.png" alt="" class="img-fluid">
						<h4 class="my-4">Dolor Sit</h4>
						<p> Ut enim ad minim veniam, quis nostrud.Excepteur sint occaecat cupidatat non proident</p>
						<a href="about.jsp" class="button-w3ls btn mt-sm-5 mt-4">Read More</a>
					</div>
				</div>
				<div class="col-lg-4 about-grid my-lg-0 my-5">
					<div class="about-grid-main">
						<img src="images/img2.png" alt="" class="img-fluid">
						<h4 class="my-4">Conse Tetur</h4>
						<p>Ut enim ad minim veniam, quis nostrud.Excepteur sint occaecat cupidatat non proident</p>
						<a href="about.jsp" class="button-w3ls btn mt-sm-5 mt-4">Read More</a>
					</div>
				</div>
				<div class="col-lg-4 about-grid">
					<div class="about-grid-main">
						<img src="images/img3.png" alt="" class="img-fluid">
						<h4 class="my-4">Adip Cing</h4>
						<p>Ut enim ad minim veniam, quis nostrud.Excepteur sint occaecat cupidatat non proident</p>
						<a href="about.jsp" class="button-w3ls btn mt-sm-5 mt-4">Read More</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- //what we do section -->

	<!-- middle -->
	<section class="midd-w3 py-5" id="faq">
		<div class="container py-xl-5 py-lg-3">
			<div class="row">
				<div class="col-lg-6 about-right-faq">
					<h6>Business Consultancy</h6>
					<h3 class="text-da">25 Years of Industry Experience</h3>
					<p class="mt-4">Integer pulvinar leo id viverra feugiat. Pellen tesque libero ut justo,
						ultrices in ligula. Semper at tempufddfel, ultrices in ligula.</p>
					<ul class="w3l-right-book mt-4">
						<li>Marketing Base</li>
						<li>Financial Consulting</li>
						<li>Investment Advice</li>
						<li>Business Growth</li>
						<li>Technical Support</li>
					</ul>
					<a href="about.jsp" class="btn button-style button-style-2 mt-sm-5 mt-4">Read More</a>
				</div>
				<div class="col-lg-6 left-wthree-img text-right">
					<img src="images/b1.png" alt="" class="img-fluid mt-5" />
				</div>
			</div>
		</div>
	</section>
	<!-- //middle -->
	<div class="tlinks">Collect from <a href="http://www.cssmoban.com/">自助建站</a></div>

	<!-- services -->
	<section class="banner-bottom-w3layouts bg-li py-5" id="services">
		<div class="container py-xl-5 py-lg-3">
			<h3 class="tittle text-center font-weight-bold">我们的服务</h3>
			<p class="sub-tittle text-center mt-3 mb-sm-5 mb-4">Sed do eiusmod tempor incididunt ut labore et dolore
				magna
				aliqua. Ut enim ad minim veniam, quis nostrud exercitation</p>
			<div class="row pt-lg-4">
				<div class="col-lg-4 about-in text-center">
					<div class="card">
						<div class="card-body">
							<div class="bg-clr-w3l">
								<span class="fa fa-line-chart"></span>
							</div>
							<h5 class="card-title mt-4 mb-3">Business Growth</h5>
							<p class="card-text">Lorem ipsum dolor sit amet consectetur elit,Adipisicing elit tempor.
							</p>
						</div>
					</div>
				</div>
				<div class="col-lg-4 about-in text-center my-lg-0 my-3">
					<div class="card active">
						<div class="card-body">
							<div class="bg-clr-w3l">
								<span class="fa fa-usd"></span>
							</div>
							<h5 class="card-title mt-4 mb-3">Financial Planning</h5>
							<p class="card-text">Lorem ipsum dolor sit amet consectetur elit,Adipisicing elit tempor.
							</p>
						</div>
					</div>
				</div>
				<div class="col-lg-4 about-in text-center">
					<div class="card">
						<div class="card-body">
							<div class="bg-clr-w3l">
								<span class="fa fa-lightbulb-o"></span>
							</div>
							<h5 class="card-title mt-4 mb-3">Business 服务</h5>
							<p class="card-text">Lorem ipsum dolor sit amet consectetur elit,Adipisicing elit tempor.
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- //services -->

	<!-- stats -->
	<section class="bottom-count py-5" id="stats">
		<div class="container py-xl-5 py-lg-3">
			<div class="row">
				<div class="col-lg-5 left-img-w3ls">
					<img src="images/b2.png" alt="" class="img-fluid" />
				</div>
				<div class="col-lg-7 right-img-w3ls pl-lg-4 mt-lg-2 mt-4">
					<div class="bott-w3ls mr-xl-5">
						<h3 class="title-w3 text-bl mb-3 font-weight-bold">Some of Company Real Facts</h3>
						<p class="title-sub-2 mb-3">Excepteur sint occaecat cupidatat <br>non proident.</p>
						<p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque
							laudantium. </p>
					</div>
					<div class="row mt-5">
						<div class="col-sm-4 count-w3ls">
							<h4>1000+</h4>
							<p>Project completed</p>
						</div>
						<div class="col-sm-4 count-w3ls mt-sm-0 mt-3">
							<h4>480+</h4>
							<p>Consultant</p>
						</div>
						<div class="col-sm-4 count-w3ls mt-sm-0 mt-3">
							<h4>600+</h4>
							<p>Award Winning</p>
						</div>
					</div>
					<div class="row mt-sm-5 mt-4">
						<div class="col-sm-4 count-w3ls">
							<h4>480+</h4>
							<p>Consultant</p>
						</div>
						<div class="col-sm-4 count-w3ls mt-sm-0 mt-3">
							<h4>600+</h4>
							<p>Award Winning</p>
						</div>
						<div class="col-sm-4 count-w3ls mt-sm-0 mt-3">
							<h4>1000+</h4>
							<p>Project completed</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- //stats -->

	<!-- partners -->
	<section class="partners py-5" id="partners">
		<div class="container py-xl-5 py-lg-3">
			<h3 class="tittle text-center font-weight-bold">Our Partners</h3>
			<p class="sub-tittle text-center mt-3 mb-sm-5 mb-4">Sed do eiusmod tempor incididunt ut labore et dolore
				magna
				aliqua. Ut enim ad minim veniam, quis nostrud exercitation</p>
			<div class="row grid-part text-center pt-4">
				<div class="col-md-2 col-4">
					<div class="parts-w3ls">
						<a href="#"><span class="fa fa-angellist"></span></a>
						<h4>Angellist</h4>
					</div>
				</div>
				<div class="col-md-2 col-4">
					<div class="parts-w3ls">
						<a href="#"><span class="fa fa-opencart"></span></a>
						<h4>opencart</h4>
					</div>
				</div>
				<div class="col-md-2 col-4">
					<div class="parts-w3ls">
						<a href="#"><span class="fa fa-lastfm"></span></a>
						<h4>lastfm</h4>
					</div>
				</div>
				<div class="col-md-2 col-4 mt-md-0 mt-4">
					<div class="parts-w3ls">
						<a href="#"><span class="fa fa-openid"></span></a>
						<h4>openid</h4>
					</div>
				</div>
				<div class="col-md-2 col-4 mt-md-0 mt-4">
					<div class="parts-w3ls">
						<a href="#"><span class="fa fa-skyatlas"></span></a>
						<h4>skyatlas</h4>
					</div>
				</div>
				<div class="col-md-2 col-4 mt-md-0 mt-4">
					<div class="parts-w3ls">
						<a href="#"><span class="fa fa-ravelry"></span></a>
						<h4>ravelry</h4>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- //partners -->

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