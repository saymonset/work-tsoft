// USE WORDWRAP AND MAXIMIZE THE WINDOW TO SEE THIS FILE
c_styles={};c_menus={}; // do not remove this line

// You can remove most comments from this file to reduce the size if you like.




/******************************************************
	(1) GLOBAL SETTINGS
*******************************************************/

c_hideTimeout=500; // 1000==1 second
c_subShowTimeout=250;
c_keepHighlighted=true;
c_findCURRENT=false; // find the item linking to the current page and apply it the CURRENT style class
c_findCURRENTTree=true;
c_overlapControlsInIE=true;
c_rightToLeft=false; // if the menu text should have "rtl" direction (e.g. Hebrew, Arabic)




/******************************************************
	(2) MENU STYLES (CSS CLASSES)
*******************************************************/

// You can define different style classes here and then assign them globally to the menu tree(s)
// in section 3 below or set them to any UL element from your menu tree(s) in the page source


c_imagesPath=""; // path to the directory containing the menu images


c_styles['MM']=[ // MainMenu (the shorter the class name the better)
[
// MENU BOX STYLE
1,		// BorderWidth
'solid',	// BorderStyle (CSS valid values except 'none')
'#b2b2b2',	// BorderColor ('color')
0,		// Padding
'[/dali-divint/images/fondo_menu_main.gif]',	// Background ('color','transparent','[image_source]')
'',		// IEfilter (only transition filters work well - not static filters)
''		// Custom additional CSS for the menu box (valid CSS)
],[
// MENU ITEMS STYLE
0,		// BorderWidth
'solid',	// BorderStyle (CSS valid values except 'none')
'solid',	// OVER BorderStyle
'#b2b2b2',	// BorderColor ('color')
'#b2b2b2',	// OVER BorderColor
4,		// Padding
'[/dali-divint/images/fondo_menu_main.gif]',	// Background ('color','transparent','[image_source]')
'[/dali-divint/images/fondo_menu_main.gif]',	// OVER Background
'#ffffff',	// Color
'#ffffff',	// OVER Color
'12px',		// FontSize (values in CSS valid units - %,em,ex,px,pt)
'Arial',	// FontFamily
'bold',		// FontWeight (CSS valid values - 'bold','normal','bolder','lighter','100',...,'900')
'none',		// TextDecoration (CSS valid values - 'none','underline','overline','line-through')
'none',		// OVER TextDecoration
'left',		// TextAlign ('left','center','right','justify')
1,		// ItemsSeparatorSize
'solid',	// ItemsSeparatorStyle (border-style valid values)
'#ffffff',	// ItemsSeparatorColor ('color','transparent')
0,		// ItemsSeparatorSpacing
true,			// UseSubMenuImage (true,false)
'[/dali-divint/images/004.gif]',	// SubMenuImageSource ('[image_source]')
'[/dali-divint/images/004.gif]',	// OverSubMenuImageSource
5,			// SubMenuImageWidth
9,			// SubMenuImageHeight
'8',			// SubMenuImageVAlign ('pixels from item top','middle')
'solid',		// VISITED BorderStyle
'#ffffff',		// VISITED BorderColor
'[/dali-divint/images/fondo_menu_main.gif]',		// VISITED Background
'#ffffff',		// VISITED Color
'none',			// VISITED TextDecoration
'[/dali-divint/images/004.gif]',	// VISITED SubMenuImageSource
'solid',		// CURRENT BorderStyle
'#ffffff',		// CURRENT BorderColor
'[/dali-divint/images/fondo_menu_main.gif]',		// CURRENT Background
'#ffffff',		// CURRENT Color
'none',			// CURRENT TextDecoration
'[/dali-divint/images/004.gif]',	// CURRENT SubMenuImageSource
'',		// Custom additional CSS for the items (valid CSS)
'',		// OVER Custom additional CSS for the items (valid CSS)
'',		// CURRENT Custom additional CSS for the items (valid CSS)
''		// VISITED Custom additional CSS for the items (valid CSS)
]];


c_styles['SM']=[ // SubMenus
[
// MENU BOX STYLE
1,		// BorderWidth
'solid',	// BorderStyle (CSS valid values except 'none')
'#b2b2b2',	// BorderColor ('color')
3,		// Padding
'#2d2f5b',	// Background ('color','transparent','[image_source]')
'',		// IEfilter (only transition filters work well - not static filters)
''		// Custom additional CSS for the menu box (valid CSS)
],[
// MENU ITEMS STYLE
0,		// BorderWidth
'solid',	// BorderStyle (CSS valid values except 'none')
'solid',	// OVER BorderStyle
'#b2b2b2',	// BorderColor ('color')
'#b2b2b2',	// OVER BorderColor
3,		// Padding
'#2d2f5b',	// Background ('color','transparent','[image_source]')
'#b2b2b2',	// OVER Background
'#ffffff',	// Color
'#ffffff',	// OVER Color
'12px',		// FontSize (values in CSS valid units - %,em,ex,px,pt)
'Arial',	// FontFamily
'normal',	// FontWeight (CSS valid values - 'bold','normal','bolder','lighter','100',...,'900')
'none',		// TextDecoration (CSS valid values - 'none','underline','overline','line-through')
'none',		// OVER TextDecoration
'left',		// TextAlign ('left','center','right','justify')
0,		// ItemsSeparatorSize
'solid',	// ItemsSeparatorStyle (border-style valid values)
'#b2b2b2',	// ItemsSeparatorColor ('color','transparent')
2,		// ItemsSeparatorSpacing
true,			// UseSubMenuImage (true,false)
'[/dali-divint/images/004.gif]',	// SubMenuImageSource ('[image_source]')
'[/dali-divint/images/004.gif]',	// OverSubMenuImageSource
5,			// SubMenuImageWidth
9,			// SubMenuImageHeight
'8',			// SubMenuImageVAlign ('pixels from item top','middle')
'solid',		// VISITED BorderStyle
'#b2b2b2',		// VISITED BorderColor
'#2d2f5b',		// VISITED Background
'#ffffff',		// VISITED Color
'none',			// VISITED TextDecoration
'[/dali-divint/images/004.gif]',	// VISITED SubMenuImageSource
'solid',		// CURRENT BorderStyle
'#b2b2b2',		// CURRENT BorderColor
'#2d2f5b',		// CURRENT Background
'#ffffff',		// CURRENT Color
'none',			// CURRENT TextDecoration
'[/dali-divint/images/004.gif]',	// CURRENT SubMenuImageSource
'',		// Custom additional CSS for the items (valid CSS)
'',		// OVER Custom additional CSS for the items (valid CSS)
'',		// CURRENT Custom additional CSS for the items (valid CSS)
''		// VISITED Custom additional CSS for the items (valid CSS)
]];




/******************************************************
	(3) MENU TREE FEATURES
*******************************************************/

// Normally you would probably have just one menu tree (i.e. one main menu with sub menus).
// But you are actually not limited to just one and you can have as many menu trees as you like.
// Just copy/paste a config block below and configure it for another UL element if you like.


c_menus['indevalMenuBar']=[ // the UL element with id="indevalMenuBar"
[
// MAIN-MENU FEATURES
'horizontal',	// ItemsArrangement ('vertical','horizontal')
'fixed',	// Position ('relative','absolute','fixed')
'0px',		// X Position (values in CSS valid units- px,em,ex)
'0px',		// Y Position (values in CSS valid units- px,em,ex)
false,		// RightToLeft display of the sub menus
false,		// BottomToTop display of the sub menus
0,		// X SubMenuOffset (pixels)
0,		// Y SubMenuOffset
'10em',		// Width (values in CSS valid units - px,em,ex) (matters for main menu with 'vertical' ItemsArrangement only)
'MM',		// CSS Class (one of the defined in section 2)
false		// Open sub-menus onclick (default is onmouseover)
],[
// SUB-MENUS FEATURES
5,		// X SubMenuOffset (pixels)
1,		// Y SubMenuOffset
'auto',		// Width ('auto',values in CSS valid units - px,em,ex)
'100',		// MinWidth ('pixels') (matters/useful if Width is set 'auto')
'300',		// MaxWidth ('pixels') (matters/useful if Width is set 'auto')
'SM',		// CSS Class (one of the defined in section 2)
false		// Open sub-menus onclick (default is onmouseover)
]];