<%@ page language="java" import="java.util.*,com.rendp.auth.dto.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>MAIN | Auth</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Bootstrap Docs -->
        <link href="http://getbootstrap.com/docs-assets/css/docs.css" rel="stylesheet" media="screen">

        <!-- Bootstrap -->
        <link rel="stylesheet" media="screen" href="/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" media="screen" href="/bootstrap/css/bootstrap-theme.min.css">

        <!-- Bootstrap Admin Theme -->
        <link rel="stylesheet" media="screen" href="/bootstrap/css/bootstrap-admin-theme.css">
        <link rel="stylesheet" media="screen" href="/bootstrap/css/bootstrap-admin-theme-change-size.css">

        <!-- Custom styles -->
        <style type="text/css">
            @font-face {
                font-family: Ubuntu;
                src: url('/fonts/Ubuntu-Regular.ttf');
            }
            .bs-docs-masthead{
                background-color: #6f5499;
                background-image: linear-gradient(to bottom, #563d7c 0px, #6f5499 100%);
                background-repeat: repeat-x;
            }
            .bs-docs-masthead{
                padding: 0;
            }
            .bs-docs-masthead h1{
                color: #fff;
                font-size: 40px;
                margin: 0;
                padding: 34px 0;
                text-align: center;
            }
            .bs-docs-masthead a:hover{
                text-decoration: none;
            }
            .row-urls{
                margin-top: 4px;
            }
            .row-urls .col-md-6{
                text-align: center;
            }
            .row-urls .col-md-6 a{
                font-size: 14px;
            }
            .secondmenu{
            	font-size: 12px;
            }
            iframe{
            	border: 0px;
            }
        </style>

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
           <script type="text/javascript" src="js/html5shiv.js"></script>
           <script type="text/javascript" src="js/respond.min.js"></script>
        <![endif]-->
    </head>
    <body class="bootstrap-admin-with-small-navbar">

        <!-- main / large navbar -->
        <nav class="navbar navbar-default navbar-fixed-top bootstrap-admin-navbar" role="navigation">
        <div class="container">
			<div class="col-lg-12">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".main-navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">权限管理</a>

				</div>

				<div class="navbar-header navbar-right">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a href="#" role="button"
							class="dropdown-toggle" data-hover="dropdown"> <i
								class="glyphicon glyphicon-user"></i> Hi <%=(String) request.getAttribute("username")%>
								<i class="caret"></i></a>
							<ul class="dropdown-menu">
								<li><a href="#">Action</a></li>
								<li><a href="#">Another action</a></li>
								<li><a href="#">Setting</a></li>
								<li role="presentation" class="divider"></li>
								<li><a href="/logout">Logout</a></li>
							</ul></li>
					</ul>
				</div>
			</div>
		</div><!-- /.container -->
        </nav>

        <div class="container">
	        <!-- <iframe name="leftFrame"> -->
	        	<div class="row col-md-2">
	                <!-- left, vertical navbar -->
	                <div class="bootstrap-admin-col-left">
	                    <ul class="nav navbar-collapse collapse bootstrap-admin-navbar-side">
	                    	<li>
	                    	<%
	                    	List<Accordion> accordions = (List<Accordion>) request.getAttribute("accordions");
	                    	int i = 0;
	                    	for(Accordion accordion : accordions) {
	                    		i++;
	                    		out.print("<a href='#collape"+i+"' data-toggle='collapse'><i class='glyphicon glyphicon-chevron-down'></i> ");
	                    		out.print(accordion.getName());
	                    		out.println("</a>");
	                    		
	                    		if(accordion.getChildren().size() > 0) {
	                    			out.println("<ul id='collape"+i+"' class='nav navbar-list bootstrap-admin-navbar-side'>");
	                    			for(Accordion child : accordion.getChildren()) {
	                            		out.print("<li class='secondmenu'><a href='");
	                            		out.print(child.getUrl());
	                            		out.print("' target='rightFrame'><i class='glyphicon glyphicon-chevron-right'></i>&nbsp;&nbsp;");
	                            		out.print(child.getName());
	                            		out.println("</a></li>");
	                            	}
	                    			out.println("</ul>");
	                    		}
	                    		
	                    	}
	                    
	                    	%>
	                    	</li>
	                    
	                        <li>
	                            <a href="/about.html"><i class="glyphicon glyphicon-chevron-right"></i> About</a>
	                        </li>
	                    </ul>
	                </div>
	            </div>
	        <!-- </iframe> -->
	        <div class="col-md-10">
		        <iframe name="rightFrame" height="450px" width="100%">
		        	
		        </iframe>
	        </div>
        </div>

        <!-- footer -->
        <div class="navbar navbar-footer">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <footer role="contentinfo">
                            <p class="text-center">&copy; 2016 rendp</p>
                        </footer>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="/js/jquery-3.1.0.min.js"></script>
        <script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/bootstrap/js/twitter-bootstrap-hover-dropdown.min.js"></script>
        <script type="text/javascript" src="/bootstrap/js/bootstrap-admin-theme-change-size.js"></script>
        <script>
        	/* $(function(){
        		$("li").click(function(){
        			$("li").removeClass('active');
        			$(this).addClass('active');
        		})
        		$(".secondmenu").click(function(){
        			
        			$(this).addClass('active')
        		})
        	}) */
        </script>
    </body>
</html>