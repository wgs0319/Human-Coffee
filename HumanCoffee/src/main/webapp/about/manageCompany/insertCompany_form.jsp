<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>연혁 등록</title>
    <style>
        /* 전체 페이지 스타일 */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Nanum Gothic', 'Malgun Gothic', sans-serif;
            background: linear-gradient(135deg, #8B4513 0%, #A0522D 50%, #CD853F 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding: 20px;
            position: relative;
            overflow-x: hidden;
        }

        /* 커피 빈 배경 효과 */
        body::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-image: 
                radial-gradient(circle at 20% 80%, rgba(139, 69, 19, 0.1) 0%, transparent 50%),
                radial-gradient(circle at 80% 20%, rgba(160, 82, 45, 0.1) 0%, transparent 50%),
                radial-gradient(circle at 40% 40%, rgba(205, 133, 63, 0.1) 0%, transparent 50%);
            pointer-events: none;
        }

        /* 연혁 등록 폼 컨테이너 */
        .insertHistory-form {
            background: rgba(255, 248, 240, 0.95);
            backdrop-filter: blur(15px);
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 20px 40px rgba(139, 69, 19, 0.3);
            width: 100%;
            max-width: 500px;
            border: 2px solid rgba(205, 133, 63, 0.3);
            position: relative;
            overflow: hidden;
            margin: 20px 0;
        }

        .insertHistory-form::before {
            content: '';
            position: absolute;
            top: -50%;
            left: -50%;
            width: 200%;
            height: 200%;
            background: linear-gradient(
                45deg,
                transparent,
                rgba(205, 133, 63, 0.05),
                transparent,
                rgba(160, 82, 45, 0.05),
                transparent
            );
            border-radius: 20px;
            pointer-events: none;
            animation: shimmer 3s ease-in-out infinite;
        }

        @keyframes shimmer {
            0%, 100% { transform: rotate(0deg); }
            50% { transform: rotate(45deg); }
        }

        /* 폼 제목 */
        .insertHistory-form h2 {
            text-align: center;
            color: #5D4037;
            margin-bottom: 30px;
            font-size: 24px;
            font-weight: 700;
            letter-spacing: 1px;
            text-shadow: 1px 1px 2px rgba(93, 64, 55, 0.1);
            position: relative;
            padding-bottom: 15px;
        }

        .insertHistory-form h2::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 80px;
            height: 3px;
            background: linear-gradient(90deg, #8D6E63, #5D4037, #8D6E63);
            border-radius: 2px;
        }

        /* 폼 그룹 스타일 */
        .form-group, .insertHistory-form > div {
            margin-bottom: 25px;
            position: relative;
        }

        /* 라벨 스타일 */
        .insertHistory-form label {
            display: block;
            margin-bottom: 8px;
            color: #5D4037;
            font-weight: 600;
            font-size: 14px;
            transition: color 0.3s ease;
        }

        /* 입력 필드 스타일 */
        .insertHistory-form input[type="text"],
        .insertHistory-form input[type="date"],
        .insertHistory-form select,
        .insertHistory-form textarea {
            width: 100%;
            padding: 15px 20px;
            border: 2px solid #D7CCC8;
            border-radius: 12px;
            font-size: 16px;
            transition: all 0.3s ease;
            background: rgba(255, 255, 255, 0.9);
            color: #5D4037;
            font-family: inherit;
        }

        .insertHistory-form textarea {
            resize: vertical;
            min-height: 120px;
        }

        .insertHistory-form input[type="text"]:focus,
        .insertHistory-form input[type="date"]:focus,
        .insertHistory-form select:focus,
        .insertHistory-form textarea:focus {
            outline: none;
            border-color: #8D6E63;
            box-shadow: 0 0 0 3px rgba(141, 110, 99, 0.2);
            background: rgba(255, 255, 255, 1);
            transform: translateY(-2px);
        }

        /* 플레이스홀더 스타일 */
        .insertHistory-form input::placeholder,
        .insertHistory-form textarea::placeholder {
            color: #A1887F;
            font-style: italic;
        }

        /* 셀렉트 박스 추가 스타일 */
        .insertHistory-form select {
            cursor: pointer;
            appearance: none;
            background-image: url("data:image/svg+xml;charset=UTF-8,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%235D4037'><path d='M7 10l5 5 5-5z'/></svg>");
            background-repeat: no-repeat;
            background-position: right 15px center;
            background-size: 20px;
            padding-right: 45px;
        }

        /* 버튼 그룹 스타일 */
        .btn-group {
            display: flex;
            gap: 15px;
            margin-top: 30px;
        }

        /* 등록 버튼 스타일 */
        .insertHistory-form button[type="submit"] {
            flex: 1;
            padding: 16px;
            background: linear-gradient(135deg, #8D6E63 0%, #5D4037 50%, #3E2723 100%);
            color: white;
            border: none;
            border-radius: 12px;
            font-size: 18px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-transform: uppercase;
            letter-spacing: 1px;
            position: relative;
            overflow: hidden;
        }

        .insertHistory-form button[type="submit"]::before {
            content: '';
            position: absolute;
            top: 50%;
            left: 50%;
            width: 0;
            height: 0;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 50%;
            transform: translate(-50%, -50%);
            transition: all 0.6s ease;
        }

        .insertHistory-form button[type="submit"]:hover::before {
            width: 300px;
            height: 300px;
        }

        .insertHistory-form button[type="submit"]:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 30px rgba(139, 69, 19, 0.4);
            background: linear-gradient(135deg, #795548 0%, #4E342E 50%, #2E1A0E 100%);
        }

        .insertHistory-form button[type="submit"]:active {
            transform: translateY(-1px);
        }

        /* 기타 버튼 스타일 */
        .btn {
            display: inline-block;
            flex: 1;
            padding: 16px 20px;
            background: transparent;
            color: #8D6E63;
            border: 2px solid #8D6E63;
            border-radius: 12px;
            font-size: 16px;
            font-weight: 600;
            text-decoration: none;
            text-align: center;
            cursor: pointer;
            transition: all 0.3s ease;
            box-sizing: border-box;
            position: relative;
            overflow: hidden;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .btn::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(141, 110, 99, 0.1), transparent);
            transition: all 0.6s ease;
        }

        .btn:hover::before {
            left: 100%;
        }

        .btn:hover {
            background: #8D6E63;
            color: white;
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(141, 110, 99, 0.3);
            border-color: #5D4037;
        }

        .btn:active {
            transform: translateY(0px);
        }

        /* 애니메이션 효과 */
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translate3d(0, 40px, 0);
            }
            to {
                opacity: 1;
                transform: translate3d(0, 0, 0);
            }
        }

        .insertHistory-form {
            animation: fadeInUp 0.8s ease-out;
        }

        /* 반응형 디자인 */
        @media (max-width: 768px) {

            .insertHistory-form {
                max-width: 450px;
                margin: 10px 0;
            }
            
            .btn-group {
                flex-direction: column;
                gap: 10px;
            }
        }

        @media (max-width: 480px) {
            body {
                padding: 5px;
            }
            
            .insertHistory-form {
                padding: 25px 20px;
                margin: 5px 0;
            }
            
            .insertHistory-form h2 {
                font-size: 18px;
                margin-bottom: 25px;
            }
            
            .insertHistory-form input[type="text"],
            .insertHistory-form input[type="date"],
            .insertHistory-form select,
            .insertHistory-form textarea {
                padding: 12px 15px;
                font-size: 14px;
            }
            
            .insertHistory-form button[type="submit"] {
                padding: 14px;
                font-size: 16px;
            }
            
            .btn {
                padding: 14px 15px;
                font-size: 14px;
            }
            
            .btn-group {
                flex-direction: column;
                gap: 10px;
                margin-top: 20px;
            }
            
            .form-group {
                margin-bottom: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="container comhistory_container">
        <div class="comhistory_title">연혁 등록</div>
        
        <div class="comhistory-box comhistory_box">
            <div class="form-container">
                <h3 class="form-title">연혁 정보 입력</h3>
                
                <form id="comHistoryForm" method="POST" action="comhistory_insert_process.jsp">
                    <div class="form-group">
                        <label for="comId">회사 ID:</label>
                        <input type="text" id="comId" name="comId" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="historyId">연혁 ID:</label>
                        <input type="text" id="historyId" name="historyId" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="startDate">개업일:</label>
                        <input type="date" id="startDate" name="startDate">
                    </div>
                    
                    <div class="form-group">
                        <label for="endDate">폐업일:</label>
                        <input type="date" id="endDate" name="endDate">
                    </div>
                    
                    <div class="form-group">
                        <label for="title">기업활동:</label>
                        <input type="text" id="title" name="title" placeholder="기업활동 내용을 입력하세요">
                    </div>
                    
                    <div class="form-group">
                        <label for="content">내용:</label>
                        <textarea id="content" name="content" rows="5" placeholder="연혁 상세 내용을 입력하세요"></textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="status">상태:</label>
                        <select id="status" name="status">
                            <option value="0">운영중</option>
                            <option value="1">폐업</option>
                        </select>
                    </div>
                    
                    <div class="btn-group form-buttons">
                        <button type="submit" class="btn btn-primary">등록</button>
                        <button type="button" class="btn btn-secondary" onclick="resetForm()">초기화</button>
                        <button type="button" class="btn btn-cancel" onclick="goBack()">취소</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        // 폼 초기화
        function resetForm() {
            document.getElementById('comHistoryForm').reset();
        }
        
        // 이전 페이지로 돌아가기
        function goBack() {
            if (confirm('작성 중인 내용이 있습니다. 정말 취소하시겠습니까?')) {
                history.back();
            }
        }
        
        // 폼 유효성 검사
        document.getElementById('comHistoryForm').addEventListener('submit', function(e) {
            const startDate = document.getElementById('startDate').value;
            const endDate = document.getElementById('endDate').value;
            
            if (startDate && endDate && startDate > endDate) {
                e.preventDefault();
                alert('개업일은 폐업일보다 이전이어야 합니다.');
                return false;
            }
            
            const comId = document.getElementById('comId').value.trim();
            const historyId = document.getElementById('historyId').value.trim();
            
            if (!comId || !historyId) {
                e.preventDefault();
                alert('회사 ID와 연혁 ID는 필수 입력 항목입니다.');
                return false;
            }
        });
        
        // 페이지 로드 시 첫 번째 입력 필드에 포커스
        window.onload = function() {
            document.getElementById('comId').focus();
        };
    </script>
</body>
</html>