// Swiper 초기화 및 추가 기능들
document.addEventListener('DOMContentLoaded', function() {
  const swiper = new Swiper('.mySwiper', {
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    pagination: {
      el: '.swiper-pagination',
      clickable: true,
    },
    loop: true,
    autoplay: {
      delay: 3000,
    },
  });

  // 기본 네비게이션 버튼 스타일 완전 제거 및 커스텀 화살표 적용
  setTimeout(() => {
    const nextBtn = document.querySelector('.swiper-button-next');
    const prevBtn = document.querySelector('.swiper-button-prev');

    if (nextBtn) {
      // 기본 스타일 제거
      nextBtn.innerHTML = '';
      nextBtn.style.color = 'transparent';
      nextBtn.style.fontSize = '0';

      // 커스텀 화살표 추가
      const nextArrow = document.createElement('div');
      nextArrow.className = 'custom-arrow custom-arrow-next';
      nextBtn.appendChild(nextArrow);
    }

    if (prevBtn) {
      // 기본 스타일 제거
      prevBtn.innerHTML = '';
      prevBtn.style.color = 'transparent';
      prevBtn.style.fontSize = '0';

      // 커스텀 화살표 추가
      const prevArrow = document.createElement('div');
      prevArrow.className = 'custom-arrow custom-arrow-prev';
      prevBtn.appendChild(prevArrow);
    }
  }, 100);

  // 검색 기능 (현재 main.jsp에는 없지만 추후 사용 가능)
  const searchEl = document.querySelector('.search');
  if (searchEl) {
    const searchInputEl = searchEl.querySelector('input');

    if (searchInputEl) {
      searchEl.addEventListener('click', function() {
        searchInputEl.focus();
      });

      searchInputEl.addEventListener('focus', function() {
        searchEl.classList.add('focused');
        searchInputEl.setAttribute('placeholder', '통합검색');
      });

      searchInputEl.addEventListener('blur', function() {
        searchEl.classList.remove('focused');
        searchInputEl.setAttribute('placeholder', '');
      });
    }
  }

  // 현재 년도 표시 (현재 main.jsp에는 없지만 추후 사용 가능)
  const thisYear = document.querySelector('.this-year');
  if (thisYear) {
    thisYear.textContent = new Date().getFullYear();
  }

  // 경력 년수 계산 (1999년 창립 기준) - main.jsp의 #years 요소에 적용
  const yearsEl = document.getElementById('years');
  if (yearsEl) {
    const currentYear = new Date().getFullYear();
    const foundedYear = 1999;
    const years = currentYear - foundedYear;
    yearsEl.textContent = years + '년';
  }
});

// 스크롤 효과 (헤더가 있을 때 작동)
window.addEventListener('scroll', function() {
  const header = document.querySelector('header');
  if (header) {
    if (window.scrollY > 100) {
      header.style.background = 'rgba(246, 245, 240, 0.95)';
      header.style.backdropFilter = 'blur(10px)';
    } else {
      header.style.background = '#F6F5F0';
      header.style.backdropFilter = 'none';
    }
  }
});

var player;
function onYouTubeIframeAPIReady() {
  player = new YT.Player('player', {
    videoId: 'An6LvWQuj_8', // 재생할 유투브 영상 ID
    playerVars: {
      autoplay: true,  // 자동 재생
      loop: true, // 반복 재생
      playlist: 'An6LvWQuj_8', // 반복 재생할 유튜브 영상 ID
    },
    events: {
      onReady: function (event) {
        event.target.mute();  // 재생시 음소거
      }
    }
  });
}

// <!-- JS: 창립일 기준 기술 경력 자동 계산 -->
  const foundationYear = 2025; // 창립연도
  const currentYear = new Date().getFullYear();
  document.getElementById("years").textContent = (currentYear - foundationYear) + "년";
