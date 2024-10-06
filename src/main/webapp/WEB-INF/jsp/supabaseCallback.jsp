<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>인증 처리 중</title>
    <script src="https://cdn.jsdelivr.net/npm/@supabase/supabase-js@2"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 600px;
            margin-top: 100px;
        }
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .card-body {
            padding: 40px;
        }
        h2 {
            color: #007bff;
            margin-bottom: 20px;
        }
        #loadingSpinner {
            width: 3rem;
            height: 3rem;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="card">
            <div class="card-body text-center">
                <h2>인증 처리 중</h2>
                <p class="lead mb-4">잠시만 기다려주세요. 로그인 정보를 확인하고 있습니다.</p>
                <div class="spinner-border text-primary mb-4" id="loadingSpinner" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
                <p id="statusMessage" class="text-muted"></p>
            </div>
        </div>
    </div>
    <script>
        // Supabase 클라이언트 초기화
        const supabaseUrl = '${supabaseUrl}';
        const supabaseAnonKey = '${supabaseAnonKey}';
        const client = supabase.createClient(supabaseUrl, supabaseAnonKey);

        async function handleCallback() {
            try {
                statusMessage.textContent = "세션 정보를 확인하는 중...";
                const { data, error } = await client.auth.getSession();
                if (error) throw error;

                if (data?.session) {
                    statusMessage.textContent = "토큰 검증 중...";
                    const response = await fetch('/auth/verify-token', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({ accessToken: data.session.access_token }),
                    });
                    const result = await response.text();

                    if (response.ok && result === 'success') {
                        statusMessage.textContent = "인증 성공! 메인 페이지로 이동합니다.";
                        setTimeout(() => { window.location.href = '/'; }, 1000);
                    } else {
                        throw new Error(result.message || 'Verification failed');
                    }
                } else {
                    throw new Error('No session');
                }
            } catch (error) {
                console.error('Error:', error);
                statusMessage.textContent = "오류가 발생했습니다. 메인 페이지로 이동합니다.";
                setTimeout(() => {
                    window.location.href = '/?error=' + encodeURIComponent(error.message);
                }, 2000);
            }
        }

        // DOMContentLoaded 이벤트를 사용하여 DOM이 완전히 로드된 후 스크립트 실행
        document.addEventListener('DOMContentLoaded', handleCallback);
    </script>
</body>
</html>