function onLoad(){

}



function onTest()
{
    document.getElementById("loader").style.display = "block";
    $.ajax({
        type: "GET",
        url: "http://localhost:8096/test",

        success: function (result) {
            console.log("success");
            document.getElementById("loader").style.display = "none";



        },
        error: function (result) {
            console.log("failure");
        }
    });



}


var _validFileExtensions = [".txt",".vm",".html",".properties",".xml"];
function validate(oForm) {
    var arrInputs = oForm.getElementsByTagName("input");
    for (var i = 0; i < arrInputs.length; i++) {
        var oInput = arrInputs[i];
        if (oInput.type == "file") {
            var sFileName = oInput.value;
            if (sFileName.length > 0) {
                var blnValid = false;
                for (var j = 0; j < _validFileExtensions.length; j++) {
                    var sCurExtension = _validFileExtensions[j];
                    if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                        blnValid = true;
                        document.getElementById("successMessage").style.display="";

                        break;
                    }
                }

                if (!blnValid) {
                    alert("Sorry, " + sFileName + " is invalid, allowed extensions are: " + _validFileExtensions.join(", "));
                    return false;
                }
            }
        }
    }

    return true;
}

