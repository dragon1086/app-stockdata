import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '@/components/Navbar';
import Footer from '@/components/Footer';
import FormInput from '@/components/FormInput';
import AutocompleteInput from '@/components/AutocompleteInput';
import { QuestionCircle } from 'lucide-react';
import { 
  DealTrainingForm, 
  FormState, 
  User, 
  DifficultyLevel,
  TooltipContent 
} from '@/types/dealTraining';

const tooltipContent: TooltipContent = {
  companyName: "시뮬레이션할 기업을 선택합니다. 기본적으로 랜덤으로 선택되며, 체크박스를 선택하면 직접 입력할 수 있습니다.",
  slotAmount: "이 종목에 투자할 총 금액을 입력합니다. 이 금액을 기준으로 매매가 이루어집니다.",
  portion: "초기 투자 비중을 설정합니다. 0%부터 100%까지 설정 가능하며, 0%는 현금 보유 상태, 100%는 풀 매수 상태를 의미합니다.",
  startDate: "시뮬레이션 시작 날짜를 선택합니다. 기본적으로 랜덤으로 선택되며, 체크박스를 선택하면 직접 입력할 수 있습니다.",
  valuationPercent: "초기 평가손익 상태를 설정합니다. 기본적으로 랜덤으로 선택되며, 체크박스를 선택하면 직접 입력할 수 있습니다.",
  level: "시뮬레이션의 난이도를 선택합니다. 초급은 -20%~+20%, 중급은 -50%~+50%, 고급은 -80%~+80%의 범위에서 시작 평가손익이 설정됩니다."
};

const DealTrainingIndex: React.FC = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState<User | null>(null);
  const [errorMessage, setErrorMessage] = useState<string>("");
  
  const [formData, setFormData] = useState<DealTrainingForm>({
    companyName: '',
    slotAmount: 0,
    portion: 0,
    startDate: '',
    valuationPercent: 0,
    level: 'beginner'
  });
  
  const [formState, setFormState] = useState<FormState>({
    isCompanyNameEnabled: false,
    isStartDateEnabled: false,
    isValuationPercentEnabled: false
  });

  // SameSite 쿠키 설정
  useEffect(() => {
    document.cookie = "SameSite=None; Secure";
  }, []);

  // 사용자 세션 정보 가져오기
  useEffect(() => {
    const fetchUserSession = async () => {
      try {
        const response = await fetch('/api/user/session');
        if (response.ok) {
          const userData = await response.json();
          setUser(userData);
        }
      } catch (error) {
        console.error('Failed to fetch user session:', error);
      }
    };

    fetchUserSession();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    try {
      const response = await fetch('/deal-calculate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        navigate('/deal-training');
      } else {
        const error = await response.json();
        setErrorMessage(error.message);
      }
    } catch (error) {
      setErrorMessage('서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const toggleInput = (field: keyof FormState) => {
    setFormState(prev => ({
      ...prev,
      [field]: !prev[field]
    }));
  };

  const handleLogin = () => {
    window.location.href = '/login/google';
  };

  const handleLogout = () => {
    window.location.href = '/logout/google';
  };

  const handleOpenHistory = () => {
    window.open('/deal-calculate-histories', '_blank');
  };

  useEffect(() => {
    // Google Tag Manager
    const script = document.createElement('script');
    script.innerHTML = `(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src='https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);})(window,document,'script','dataLayer','GTM-T8G7WJD');`;
    document.head.appendChild(script);
  }, []);

  return (
    <div className="min-h-screen flex flex-col bg-gray-50">
      <Navbar 
        user={user}
        onLogin={handleLogin}
        onLogout={handleLogout}
        onOpenHistory={handleOpenHistory}
      />

      <main className="flex-grow container mx-auto px-4 pt-16">
        {errorMessage && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            {errorMessage}
          </div>
        )}

        <div className="bg-white rounded-lg shadow-xl p-8 mt-8">
          <h1 className="text-3xl font-bold text-center mb-8">주식 매매 시뮬레이션</h1>

          <form onSubmit={handleSubmit}>
            <AutocompleteInput
              id="companyNameInput"
              label="기업 이름"
              tooltip={tooltipContent.companyName}
              value={formData.companyName}
              onChange={(value) => setFormData(prev => ({ ...prev, companyName: value }))}
              disabled={!formState.isCompanyNameEnabled}
              hasCheckbox
              isChecked={formState.isCompanyNameEnabled}
              onCheckboxChange={() => toggleInput('isCompanyNameEnabled')}
            />

            <FormInput
              id="slotAmount"
              label="종목에 배분할 금액"
              tooltip={tooltipContent.slotAmount}
              value={formData.slotAmount}
              onChange={handleInputChange}
              placeholder="배분할 총금액을 입력하세요"
            />

            <FormInput
              id="portion"
              label="시작 비중(%)"
              tooltip={tooltipContent.portion}
              value={formData.portion}
              onChange={handleInputChange}
              placeholder="% 제외하고 입력하세요(소수점 제외)"
            />

            <FormInput
              id="startDate"
              label="시작 날짜"
              tooltip={tooltipContent.startDate}
              type="date"
              value={formData.startDate}
              onChange={handleInputChange}
              disabled={!formState.isStartDateEnabled}
              hasCheckbox
              isChecked={formState.isStartDateEnabled}
              onCheckboxChange={() => toggleInput('isStartDateEnabled')}
            />

            <FormInput
              id="valuationPercent"
              label="시작 평가손익(%)"
              tooltip={tooltipContent.valuationPercent}
              value={formData.valuationPercent}
              onChange={handleInputChange}
              disabled={!formState.isValuationPercentEnabled}
              placeholder="초기 평가손익"
              hasCheckbox
              isChecked={formState.isValuationPercentEnabled}
              onCheckboxChange={() => toggleInput('isValuationPercentEnabled')}
            />

            <div className="mb-6">
              <label htmlFor="level" className="flex items-center text-gray-700 font-semibold mb-2">
                난이도 선택
                <div className="group relative ml-2">
                  <QuestionCircle className="h-5 w-5 text-blue-500 cursor-help" />
                  <div className="hidden group-hover:block absolute z-10 w-64 p-2 bg-gray-800 text-white text-sm rounded shadow-lg -right-2 top-6">
                    {tooltipContent.level}
                  </div>
                </div>
              </label>
              <select
                id="level"
                name="level"
                value={formData.level}
                onChange={handleInputChange}
                className="w-full px-4 py-2 border rounded-lg bg-white focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                <option value="beginner">초급 (-20%~+20%)</option>
                <option value="intermediate">중급 (-50%~+50%)</option>
                <option value="master">고급 (-80%~+80%)</option>
              </select>
            </div>

            <button
              type="submit"
              className="w-full bg-blue-500 text-white py-3 rounded-lg font-bold hover:bg-blue-600 transition-colors"
            >
              시뮬레이션 시작
            </button>
          </form>

          <hr className="my-8" />

          <div className="text-center">
            <button
              type="button"
              onClick={() => navigate('/dealTrainingManual')}
              className="bg-green-500 text-white px-6 py-3 rounded-lg font-bold hover:bg-green-600 transition-colors"
            >
              시뮬레이션 메뉴얼
            </button>
          </div>
        </div>
      </main>

      <Footer />

      {/* AQR Widget Script */}
      <script src="https://aq.gy/c/widget.js" />
      <script dangerouslySetInnerHTML={{
        __html: `
          new AQRWidget().renderAQRWidget({
            token: "3iMB^",
            layer_id: "aqr-widget-area",
            profile: false,
            libbutton: true,
            bgcolor: "#000000",
            textcolor: "#ffffff",
            button_text: "서버 운영비 기부하기"
          });
        `
      }} />
    </div>
  );
};

export default DealTrainingIndex;