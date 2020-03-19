function loadDoctors() {
    $.post("alldoc", null, res => {
        var $doctors = $("#doctors");
        var i = 0;
        for (const name in res) {
            if (res.hasOwnProperty(name)) {
                i++;
                const about = res[name];
                $doctors.append(
                    '<tr>' +
                    '<td>' + i + '</td>' +
                    '<td>' + name + '</td>' +
                    '<td>' + about + '</td>' +
                    '</tr>'
                );
            }
        }
    }, "json");
}

function getSchedule(e) {
    var $appointment = $("#appointment");
    $appointment.html("");
    json = {
        table: $(e).val()
    };
    console.log(json);
    $.post("dayall", json, res => {
        var $list = res.ans;
        $list.forEach(element => {
            console.log(element);
            $appointment.append(
                '<tr id="time' + element.doc_id + '">' +
                '<td>' + element.doc_id + '</td>' + //序号
                '<td style="background-color: aquamarine;">' + element.name + '</td>' + //名字
                '<td style="background-color: red;" onclick="appoint($(this))">' + element.time1 + '</td>' + //time1
                '<td style="background-color: red;" onclick="appoint($(this))">' + element.time2 + '</td>' + //time2
                '<td style="background-color: red;" onclick="appoint($(this))">' + element.time3 + '</td>' +
                '<td style="background-color: red;" onclick="appoint($(this))">' + element.time4 + '</td>' +
                '<td style="background-color: red;" onclick="appoint($(this))">' + element.time5 + '</td>' +
                '<td style="background-color: red;" onclick="appoint($(this))">' + element.time6 + '</td>' +
                '<td style="background-color: red;" onclick="appoint($(this))">' + element.time7 + '</td>' +
                '<td style="background-color: red;" onclick="appoint($(this))">' + element.time8 + '</td>' +
                '</tr>'
            );
        });
        $.post("userservice", null, res => {
            if (res.table === json.table) {
                $("#appointment tr#time" + res.doc_id + " td:eq(" + (res.timei + 1) + ")").css('background-color', 'green');
            }
        }, 'json');
    }, 'json');
}


function appoint($elem) {
    $.post("userservice", null, data => {
        var json = {
            table: $("#day").val(),
            doc_id: parseInt($elem.parent().children().eq(0).text()),
            timei: $elem.index() - 1
        };

        if (data.not === false) { //没选service
            $elem.css("background-color", 'green');
            $.post("userappointment", json, res => {
                console.log(res.count);
                $elem.text(res.count);
            }, 'json');
        } else if (check(json, data)) { //已选当前service
            $elem.css("background-color", 'red');
            $.post("usercancel", json, res => {
                console.log(res);
                $elem.text(res.count);
            }, 'json');
        } else { //已选择另一个service
            alert("已经选择了" + JSON.stringify(data));
            $.post("usercancel", data, resCount => {
                if (data.table === json.table)
                    $("#appointment tr#time" + data.doc_id + " td:eq(" + (data.timei + 1) + ")")
                    .css("background-color", 'red')
                    .text(resCount.count);
            }, 'json');
            $.post("userappointment", json, res => {
                $elem.css("background-color", 'green');
                $elem.text(res.count);
            }, 'json');
        }
    }, 'json');
}


function check(json1, json2) {
    if (json1.table !== json2.table)
        return false;

    if (json1.doc_id !== json2.doc_id)
        return false;

    if (json1.timei !== json2.timei)
        return false;

    return true;
}