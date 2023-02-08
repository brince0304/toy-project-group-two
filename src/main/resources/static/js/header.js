
    function logout() {
    if (confirm("로그아웃 하시겠습니까?")) {
    $.ajax({
        type : "GET",
        url : "/account/logout",
        success : function(data) {
            alert("로그아웃 되었습니다.");
            location.href = "/account/login";
        }
    });
    }
    }

