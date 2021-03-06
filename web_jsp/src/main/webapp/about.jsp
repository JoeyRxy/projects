<%@page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html>
<html lang="zxx">

<head>
	<title>关于我们</title>
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
								<li><a href="about.jsp" class="active">关于我们</a></li>
								<li><a href="pricing.jsp">价格</a></li>
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
										<li><a href="#stats" class="drop-text">Statistics</a></li>
										<li><a href="#why" class="drop-text">Why Choose Us?</a></li>
										<li><a href="#team" class="drop-text">Our Team</a></li>
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
		<div class="banner_w3lspvt-2">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="index.jsp" class="font-weight-bold">主页</a></li>
				<li class="breadcrumb-item" aria-current="page">关于我们</li>
			</ol>
		</div>
		<!-- //banner -->
	</div>
	<!-- //main banner -->

	<!-- about -->
	<div class="about-inner py-5">
		<div class="container pb-xl-5 pb-lg-3">
			<div class="row py-xl-4">
				<div class="col-lg-5 about-right-faq pr-5">
					<h6>Story 关于我们</h6>
					<h3 class="mt-2 mb-3">Welcome to our Website</h3>
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse porta erat sit.</p>
					<p class="mt-2">Suspendisse porta erat sit amet eros sagittis, quis hendrerit libero aliquam. Fusce
						semper augue
						ac dolor
						efficitur.</p>
					<a href="about.jsp" class="btn button-style mt-sm-5 mt-4">Read More</a>
				</div>
				<div class="col-lg-7 welcome-right text-center mt-lg-0 mt-5">
					<img src="images/about.png" alt="" class="img-fluid" />
				</div>
			</div>
		</div>
	</div>
	<!-- //about -->

	<!-- why -->
	<section class="why bg-li py-5" id="why">
		<div class="container py-xl-5 py-3">
			<div class="row">
				<div class="col-md-4">
					<div class="ser-grd">
						<div class="row">
							<div class="col-md-3 col-2 text-center services-icon icon-clr5">
								<span class="fa fa-handshake-o"></span>
							</div>
							<div class="col-md-9 col-10 text-left services-grid">
								<h4>Title Here</h4>
								<p>Natus error sit voluptatem accusantium dolo remque lauda ntium.</p>
							</div>
						</div>
					</div>
					<div class="ser-grd mt-md-5 mt-4">
						<div class="row">
							<div class="col-md-3 col-2 text-center services-icon icon-clr6">
								<span class="fa fa-coffee"></span>
							</div>
							<div class="col-md-9 col-10 text-left services-grid">
								<h4>Title Here</h4>
								<p>Natus error sit voluptatem accusantium dolo remque lauda ntium.</p>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="ser-grd mt-md-0 mt-4">
						<div class="row">
							<div class="col-md-3 col-2 text-center services-icon icon-clr1">
								<span class="fa fa-headphones"></span>
							</div>
							<div class="col-md-9 col-10 text-left services-grid">
								<h4>Title Here</h4>
								<p>Natus error sit voluptatem accusantium dolo remque lauda ntium.</p>
							</div>
						</div>
					</div>
					<div class="ser-grd mt-md-5 mt-4">
						<div class="row">
							<div class="col-md-3 col-2 text-center services-icon icon-clr2">
								<span class="fa fa fa-eye"></span>
							</div>
							<div class="col-md-9 col-10 text-left services-grid">
								<h4>Title Here</h4>
								<p>Natus error sit voluptatem accusantium dolo remque lauda ntium.</p>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="ser-grd mt-md-0 mt-4">
						<div class="row">
							<div class="col-md-3 col-2 text-center services-icon icon-clr3">
								<span class="fa fa-folder-open"></span>
							</div>
							<div class="col-md-9 col-10 text-left services-grid">
								<h4>Title Here</h4>
								<p>Natus error sit voluptatem accusantium dolo remque lauda ntium.</p>
							</div>
						</div>
					</div>
					<div class="ser-grd mt-md-5 mt-4">
						<div class="row">
							<div class="col-md-3 col-2 text-center services-icon icon-clr4">
								<span class="fa fa-lightbulb-o"></span>
							</div>
							<div class="col-md-9 col-10 text-left services-grid">
								<h4>Title Here</h4>
								<p>Natus error sit voluptatem accusantium dolo remque lauda ntium.</p>
							</div>
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

	<!-- team -->
	<section class="team bg-li py-5" id="team">
		<div class="container py-xl-5 py-lg-3">
			<h3 class="tittle text-center font-weight-bold">Our Awesome Team</h3>
			<p class="sub-tittle text-center mt-3 mb-sm-5 mb-4">Sed do eiusmod tempor incididunt ut labore et dolore
				magna
				aliqua. Ut enim ad minim veniam, quis nostrud exercitation</p>
			<div class="row ab-info second pt-lg-4">
				<div class="col-lg-4 col-sm-6 ab-content text-center mt-lg-0 mt-4">
					<div class="ab-content-inner">
						<img src="images/t1.jpg" alt="news image" class="img-fluid">
						<div class="ab-info-con">
							<h4 class="text-team-w3">Petey Cruiser</h4>
							<ul class="list-unstyled team-socil-w3pvts">
								<li class="d-inline">
									<a href="#"><span class="fa fa-facebook"></span></a>
								</li>
								<li class="d-inline">
									<a href="#"><span class="fa fa-twitter"></span></a>
								</li>
								<li class="d-inline">
									<a href="#"><span class="fa fa-google-plus"></span></a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-lg-4 col-sm-6 ab-content text-center mt-lg-0 mt-4">
					<div class="ab-content-inner">
						<img src="images/t2.jpg" alt="news image" class="img-fluid">
						<div class="ab-info-con">
							<h4 class="text-team-w3">Mario Spe</h4>
							<ul class="list-unstyled team-socil-w3pvts">
								<li class="d-inline">
									<a href="#"><span class="fa fa-facebook"></span></a>
								</li>
								<li class="d-inline">
									<a href="#"><span class="fa fa-twitter"></span></a>
								</li>
								<li class="d-inline">
									<a href="#"><span class="fa fa-google-plus"></span></a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-lg-4 col-sm-6 ab-content text-center mt-lg-0 mt-4">
					<div class="ab-content-inner">
						<img src="images/t3.jpg" alt="news image" class="img-fluid">
						<div class="ab-info-con">
							<h4 class="text-team-w3">Turn Paige</h4>
							<ul class="list-unstyled team-socil-w3pvts">
								<li class="d-inline">
									<a href="#"><span class="fa fa-facebook"></span></a>
								</li>
								<li class="d-inline">
									<a href="#"><span class="fa fa-twitter"></span></a>
								</li>
								<li class="d-inline">
									<a href="#"><span class="fa fa-google-plus"></span></a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- //team -->

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

	<!-- map -->
	<div class="w3l-map p-4">
		<iframe
			src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d848295.9861393345!2d150.37152490226384!3d-33.846975938661174!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x6b129838f39a743f%3A0x3017d681632a850!2sSydney+NSW%2C+Australia!5e0!3m2!1sen!2sin!4v1515557909959"></iframe>
	</div>
	<!-- //map -->

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