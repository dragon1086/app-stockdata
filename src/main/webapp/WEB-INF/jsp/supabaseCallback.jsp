<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Authentication Callback</title>
    <script src="https://cdn.jsdelivr.net/npm/@supabase/supabase-js@2"></script>
</head>
<body>
<p>인증 처리 중입니다. 잠시만 기다려주세요...</p>

<script>
    // Supabase 클라이언트 초기화
    const supabaseUrl = '${supabaseUrl}';
    const supabaseAnonKey = '${supabaseAnonKey}';
    const client = supabase.createClient(supabaseUrl, supabaseAnonKey);

    async function handleCallback() {
        try {
            const { data, error } = await client.auth.getSession();
            if (error) throw error;

            if (data?.session) {
                const response = await fetch('/auth/verify-token', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ accessToken: data.session.access_token }),
                });
                const result = await response.text();

                if (response.ok && result === 'success') {
                    window.location.href = '/';
                } else {
                    throw new Error(result.message || 'Verification failed');
                }
            } else {
                throw new Error('No session');
            }
        } catch (error) {
            console.error('Error:', error);
            window.location.href = '/?error=' + encodeURIComponent(error.message);
        }
    }

    // DOMContentLoaded 이벤트를 사용하여 DOM이 완전히 로드된 후 스크립트 실행
    document.addEventListener('DOMContentLoaded', function() {
        handleCallback();
    });
</script>
</body>
</html>