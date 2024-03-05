
/*
========================================
 position:fixed for All Browsers v1.0.2
 Add-on for SmartMenus v6.0.2+
========================================
 (c)2008 ET VADIKOM-VASIL DINKOV
========================================
*/


// ===
c_fT = [];
c_PF = 0;
function c_pF() {
	if (typeof c_dB == c_u) {
		return;
	}
	var r, x, y, i, M, D;
	D = c_qM ? c_dB : c_dE;
	x = c_w.pageXOffset || D.scrollLeft - (c_rL() ? D.scrollWidth - D.clientWidth : 0) || 0;
	y = c_w.pageYOffset || D.scrollTop || 0;
	for (i = 0; i < c_fT.length; ) {
		r = c_fT[i++];
		M = c_menus[r.MM][0];
		r = r.style;
		
		if (M[4] && M[0] != "horizontal" || c_r) {
			if (c_iEWo) {
				if (c_rL()) {
					r.right = -x + "px";
				} else {
					r.right = "1px";
					r.right = "0";
				}
			} else {
				r.right = -x + "px";
			}
		} else {
			r.left = x + "px";
			document.getElementById("menuContainer").style.left= x + "px";
		}
		if (M[5]) {
			if (c_iEWo) {
				if (c_rL()) {
					r.bottom = -y + "px";
				} else {
					r.bottom = "1px";
					r.bottom = "0";
				}
			} else {
				r.bottom = -y + "px";
			}
		} else {
			r.top = y + "px";
			document.getElementById("menuContainer").style.top=y+"px";
		}
		
	}
}
function c_fF() {
	if (c_PF) {
		return;
	}
	if (typeof c_L != c_u) {
		c_PF = 1;
	}
	var r, m, M;
	for (m in c_menus) {
		r = c_gO(m);
		if (r && !r.PF && (!c_iEM || c_PF)) {
			r.PF = 1;
			M = c_menus[m][0];
			if (M[1] == "fixed") {
				if (c_iC) {
					r.style.position = "fixed";
				} else {
					if (!c_iEM || !M[5]) {
						c_fT[c_fT.length] = r;
					}
					if (!r.MM) {
						r.MM = m;
					}
					r = r.style;
					if (M[0] != "horizontal") {
						r[M[4] || c_r ? "marginRight" : "marginLeft"] = M[2];
						r[M[4] || c_r ? "right" : "left"] = "0";
						r[M[5] ? "marginBottom" : "marginTop"] = M[3];
						r[M[5] ? "bottom" : "top"] = "0";
					}
				}
			}
			if (!c_iC) {
				c_pF();
			}
		}
	}
	setTimeout("c_fF()", 100);
}
if (c_iEWo || c_iEM || c_gCo && !c_gC13 || c_iC && c_dl) {
	c_fF();
	if (c_iEWo) {
		c_w.attachEvent("onscroll", c_pF);
		c_w.attachEvent("onresize", c_pF);
	} else {
		if (c_iEM) {
			c_wS = c_w.onscroll ? c_w.onscroll : function () {
			};
			c_wS = c_w.onresize ? c_w.onresize : function() {
			};
			c_w.onscroll = function () {
				c_pF();
				c_wS();
			};
			c_w.onresize = function () {
				c_pF();
				c_wS();
			};
		} else {
			if (c_gCo) {
				setInterval(c_pF, 30);
			}
		}
	}
}

