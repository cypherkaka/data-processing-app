var stompClient = null;

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/payload-messages', function (payload) {
            showPayloadMessages(JSON.parse(payload.body).message);
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

    // Page connected by default
    connect();
});

