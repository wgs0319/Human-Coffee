<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.humancoffee.*" %>
<%@ page import="com.humancoffee.manager.*" %>
<%@ page import="com.humancoffee.model.*" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="com.fasterxml.jackson.core.type.TypeReference" %>
<%@ page isELIgnored="true" %>
    
<%	
	HumanCoffee hcInstance = (HumanCoffee)getServletContext().getAttribute("HumanCoffee");
	ManageOrderDetails mgDetail = hcInstance.mOrderDetails;

	
	List<Order_Detail> order_detail = new Vector<>(hcInstance.mOrderDetails.order_details[hcInstance.mCupons.memory_pos]);
	String order_id = "";
	String product_id = "";
	String member_id = (String)session.getAttribute("id");
	System.out.println("member id:" + member_id);
	int	cnt = 0;
	int	tot_price = 0;
	
	// order_detail 리스트의 크기를 확인
	int orderDetailSize = order_detail != null ? order_detail.size() : 0;
	
	// 리스트에 데이터가 있으면 첫 번째 항목의 값을 사용
	if (orderDetailSize > 0) {
		Order_Detail detail = order_detail.get(0);
		order_id = detail.getOrderId() != null ? detail.getOrderId() : "";
		product_id = detail.getProductId() != null ? detail.getProductId() : "";
		cnt = detail.getCnt() != 0 ? detail.getCnt() : 0;
		tot_price = detail.getTotPrice() != 0 ? detail.getTotPrice() : 0;
	}
	
%>    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주문 현황</title>
    <style>
        #orderStatus {
            border: 1px solid #ccc;
            padding: 10px;
            min-height: 400px;
            max-height: 600px;
            overflow-y: auto;
            white-space: pre-wrap;
            background-color: #f8f8f8;
        }
        .order-item {
            border: 1px solid #ddd;
            padding: 10px;
            margin-bottom: 10px;
            cursor: pointer;
            background-color: #fff;
            transition: all 0.3s;
            position: relative;
        }
        .order-item:hover {
            background-color: #ffe6e6;
            border-color: #ff9999;
            transform: translateX(5px);
        }
        .order-item::after {
            content: "클릭하여 삭제";
            position: absolute;
            top: 5px;
            right: 10px;
            font-size: 0.8em;
            color: #999;
            opacity: 0;
            transition: opacity 0.3s;
        }
        .order-item:hover::after {
            opacity: 1;
        }
        .delete-animation {
            animation: slideOut 0.3s ease-out forwards;
        }
        @keyframes slideOut {
            0% { transform: translateX(0); opacity: 1; }
            100% { transform: translateX(-100%); opacity: 0; }
        }
        .order-id {
            font-weight: bold;
            color: #333;
        }
        .order-details {
            font-size: 0.9em;
            color: #666;
            margin-top: 5px;
        }
        .order-status {
            font-style: italic;
            color: #007bff;
        }
        
        
        
        .manager-assigned-order {
		    background-color: #6F4E37; /* 커피색 */
		    color: #FFFFFF; /* 흰색 */
		}
		
		.manager-assigned-order .order-id,
		.manager-assigned-order .order-details,
		.manager-assigned-order .order-details span,
		.manager-assigned-order .order-status,
		.manager-assigned-order .manager-id {
		    color: #FFFFFF; /* 내부 글자색도 흰색으로 통일 */
		}
    </style>
</head>
<body>

    <h2>실시간 주문 현황 (클릭하여 삭제)</h2>
    <div id="orderStatus">
        <p>새로운 주문이 도착하면 여기에 표시됩니다.</p> 
    </div>

    <script>
        // 주문 항목 삭제 함수
        // 주문 항목 삭제 함수
// 주문 항목 삭제 함수
function deleteOrderItem(element) {
    // 클릭 이벤트가 input 필드에서 발생한 경우 삭제하지 않음
    if (event.target.tagName === 'INPUT') {
        return;
    }

    // 주문 ID 추출
    const orderIdElement = element.querySelector('.order-id');
    if (!orderIdElement) {
        console.error('Order ID element not found.');
        return;
    }
    const memberIdElement = element.querySelector('.manager-id');
    
    // `textContent`에서 "주문 ID: " 부분을 제거하고 실제 ID만 추출
    const orderId = orderIdElement.textContent.replace('주문 ID: ', '').trim();
    console.log("orderId:", orderId);

    // 담당자가 할당되지 않은 경우 (주문 담당자 배정)
    if (!memberIdElement) {
    	const memberId = '<%=member_id%>';
        console.log("memberId:", memberId);
        
        // 주문 ID를 서버로 전송
        console.log("address:", "<%= request.getContextPath() %>/order/updateorder.jsp");
        fetch(`${'<%= request.getContextPath() %>'}/order/updateorder.jsp`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `order_id=${encodeURIComponent(orderId)}&member_id=${encodeURIComponent(memberId)}`
        })
        .then(response => {
           if (response.ok) {
                console.log('Order update successful');
                // 업데이트 성공 시 항목 즉시 삭제
//                element.remove();
            } else {
                console.error('Order update failed');
                alert("주문 업데이트 실패");
            }
        })
        .catch(error => {
            console.error('Error sending data:', error);
            alert("업데이트 중 오류가 발생했습니다.");
        });
    }
    // 담당자가 이미 할당된 경우 (주문 삭제)
    else if (confirm('이 주문을 삭제하시겠습니까?')) {
        // 주문 ID를 서버로 전송
        const memberId = memberIdElement.textContent.replace('담당자 ID: ', '').trim();
        console.log("address:", "<%= request.getContextPath() %>/order/deleteorder.jsp");
        fetch(`${'<%= request.getContextPath() %>'}/order/deleteorder.jsp`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `order_id=${encodeURIComponent(orderId)}&member_id=${encodeURIComponent(memberId)}`
        })
        .then(response => response.text())
        .then(data => {
            console.log("Server response:", data);
            if (data.trim() === 'success') {
                element.classList.add('delete-animation');
                setTimeout(() => {
                    element.remove();
                }, 300);
            } else {
                alert("주문 삭제 실패: " + data);
            }
        })
        .catch(error => {
            console.error('Error deleting order:', error);
            alert("주문 삭제 중 오류가 발생했습니다.");
        });
    }
}


// 웹소켓 연결 함수
function connectWebSocket() {
	// 현재 페이지의 프로토콜 (http 또는 https)에 따라 WebSocket 프로토콜 (ws 또는 wss) 결정
	const wsProtocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';

	// window.location.host를 사용하여 'localhost:8080' 부분을 동적으로 가져옴
	const wsUri = wsProtocol + '//' + window.location.host + '<%= request.getContextPath() %>/order-websocket';

	// 예시: http://localhost:8080 에서 실행하면
	// wsUri는 "ws://localhost:8080/프로젝트명/websocket" 이 됩니다.
    const websocket = new WebSocket(wsUri);

    websocket.onopen = function(event) {
        console.log("WebSocket connected.");
    };

    // 서버로부터 메시지를 받았을 때
    websocket.onmessage = function(event) {
        const message = event.data;
        console.log("Received message:", message);
        
        try {
            const orders = JSON.parse(message);
            const orderStatusDiv = document.getElementById('orderStatus');
            
            orders.forEach(order => {
                // 동일한 order_id를 가진 항목이 이미 존재하면 해당 항목을 삭제
                const existingItem = document.querySelector(`.order-id[data-order-id="${order.order_id}"]`);
                if (existingItem) {
                    const parentItem = existingItem.closest('.order-item');
                    if (parentItem) {
                        parentItem.remove(); // 기존 항목 삭제
                    }
                }

                // 만약 order.status가 1인 경우, 항목 생성하지 않고 종료
                if(order.status == 1) {
                    return;
                }
                
                const orderItem = document.createElement('div');
                orderItem.className = 'order-item';
                
                // 주문 ID에 데이터 속성 추가
                let htmlContent = `<div class="order-id" data-order-id="${order.order_id}">주문 ID: ${order.order_id}</div>`;

                if(order.member_id && order.member_id != "null"){
                    orderItem.classList.add('manager-assigned-order');
                }
                
                // 담당자 ID가 있으면 항목 추가
                if (order.member_id && order.member_id != "null") {
                    htmlContent += `<div class="manager-id">담당자 ID: ${order.member_id}</div>`;
                }
                
                htmlContent += `<div class="order-details">고객 Tel: ${order.customer_tel} | <span class="order-status">상태: ${order.status}</span></div>`;
                
                if (order.order_bottom && Array.isArray(order.order_bottom)) {
                    htmlContent += `<div class="order-details">상품 목록:`;
                    order.order_bottom.forEach(item => {
                        htmlContent += `<br> - 제품 ID: ${item.product_id}, 제품명: ${item.product_name}, 수량: ${item.cnt}`;
                    });
                    htmlContent += `</div>`;
                }
                
                orderItem.innerHTML = htmlContent;
                
                // 클릭 이벤트 리스너 추가: 클릭 시 해당 항목 삭제
                orderItem.onclick = function(event) {
                    deleteOrderItem(this);
                };
                
                // 새로운 주문을 맨 위에 추가
                orderStatusDiv.prepend(orderItem);
            });

        } catch (e) {
            console.error("Failed to parse JSON message:", e);
            const orderStatusDiv = document.getElementById('orderStatus');
            const errorMsg = document.createElement('div');
            errorMsg.className = 'order-item';
            errorMsg.textContent = `오류: ${message}`;
            errorMsg.onclick = function(event) {
                deleteOrderItem(this);
            };
            orderStatusDiv.prepend(errorMsg);
        }
    };

    websocket.onclose = function(event) {
        console.log("WebSocket disconnected.");
        setTimeout(connectWebSocket, 5000); // 5초 후 재연결 시도
    };

    websocket.onerror = function(error) {
        console.error("WebSocket Error:", error);
    };
}

window.onload = connectWebSocket;
    </script>

</body>
</html>