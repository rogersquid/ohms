<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/2000/REC-xhtml1-20000126/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="cs" lang="cs"> 
	<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
		<title>OHMS &raquo; Template</title>
		<link rel='stylesheet' type='text/css' href='/ohms/ohms.css' />
	</head> 
	<body>
		<div id='wrapper'>
			<div id='header'>
				<!-- <a href='' id='header-image'></a> -->
			</div>
			<div id='main-nav'>
				<ul>
					<li><a href=''>Home</a></li>
					<li><a href=''>Login</a></li>
					<li><a href=''>Room availability</a></li>
					<li><a href=''>Etc</a></li>
				</ul>
			</div>
			<div id='content-wrapper'>
				<div id='left-nav'>
					<ol>
						<li class='done'>Login</li>
						<li class='done'>Choose dates</li>
						<li class='current'>Select room types</li>
						<li>Process payment</li>
					</ol>
				</div>
				<div id='content'>
					<div id='title'>Select room types and customize</div>
					<form>
						How many rooms would you like to book?
						<select name='num_rooms'>
							<option>1</option>
							<option selected="selected">2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
						<input type='submit' value='Continue' />
					</form>
					<div class='rooms'>
						<ol>
							<li>
								<span>
									<select>
										<option>0</option>
										<option>1</option>
										<option>2</option>
									</select>
									single bed(s), 
									<select>
										<option>0</option>
										<option>1</option>
										<option>2</option>
									</select>
									double bed(s)
									<br />
									<input type='checkbox' /> Television
									<br />
									<input type='checkbox' /> Internet connection
									<br />
									<input type='checkbox' /> Wake-up service
								</span>
							</li>
							<li>
								<span>
									<select>
										<option>0</option>
										<option>1</option>
										<option>2</option>
									</select>
									single bed(s), 
									<select>
										<option>0</option>
										<option>1</option>
										<option>2</option>
									</select>
									double bed(s)
									<br />
									<input type='checkbox' /> Television
									<br />
									<input type='checkbox' /> Internet connection
									<br />
									<input type='checkbox' /> Wake-up service
								</span>
							</li>
						</ol>
						<div style='text-align: center;'><input type='submit' value='Continue to payment' /></div>
					</div>
				</div>
				<div class='clear'><!-- --></div>
			</div>
			<div id='footer'>
				Online Hotel Management Service &copy; 2009<br /> by 419 Software
			</div>
		</div>
	</body>
</html>