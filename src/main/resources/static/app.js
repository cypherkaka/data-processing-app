var stompClient = null;

function connect() {
    var socket = new SockJS('/web-browser-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/payload-messages', function (payload) {
            // showPayloadMessages(JSON.parse(payload.body).message);
            showPayloadMessages(payload.body);
        });
    });
}

function showPayloadMessages(message) {
    $("#payload-messages").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    // Connection activated by default
    connect();
});

