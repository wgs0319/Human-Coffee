// Swiper 초기화 및 추가 기능들
document.addEventListener('DOMContentLoaded', function() {
  // Swiper 초기화 - Swiper 6.8.4 문법 사용
  const swiper = new Swiper('.mySwiper', {
    // 네비게이션 설정
    navigation: {
      nextEl: '.home_swiper-button-next',
      prevEl: '.home_swiper-button-prev',
    },
    // 페이지네이션 설정
    pagination: {
      el: '.home_swiper-pagination',
      clickable: true,
      dynamicBullets: true, // 동적 불릿 표시
    },
    // 기본 설정
    loop: true,
    autoplay: {
      delay: 3000,
      disableOnInteraction: false,
    },
    // 효과 설정
    effect: 'slide',
    speed: 500,
    // 반응형 설정
    breakpoints: {
      640: {
        slidesPerView: 1,
        spaceBetween: 20,
      },
      768: {
        slidesPerView: 1,
        spaceBetween: 30,
      },
      1024: {
        slidesPerView: 1,
        spaceBetween: 40,
      },
    },
  });

  // 기본 네비게이션 버튼 스타일 완전 제거 및 커스텀 화살표 적용
  setTimeout(() => {
    const nextBtn = document.querySelector('.home_swiper-button-next');
    const prevBtn = document.querySelector('.home_swiper-button-prev');

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

  // 경력 년수 계산 (통합 버전)
  const yearsEl = document.getElementById('years');
  if (yearsEl) {
    const currentYear = new Date().getFullYear();
    const foundedYear = 1999; // 25년 경력 기준으로 1999년 창립
    const years = currentYear - foundedYear;
    yearsEl.textContent = years + '년';
  }

  // 검색 기능 (추후 사용을 위해 유지)
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

  // 현재 년도 표시 (추후 사용을 위해 유지)
  const thisYear = document.querySelector('.this-year');
  if (thisYear) {
    thisYear.textContent = new Date().getFullYear();
  }
});

// 스크롤 효과 (헤더가 있을 때 작동)
window.addEventListener('scroll', function() {
  const header = document.querySelector('header');
  if (header) {
    if (window.scrollY > 100) {
      header.style.background = 'rgba(246, 245, 240, 0.95)';
      header.style.backdropFilter = 'blur(10px)';
      header.style.transition = 'all 0.3s ease';
    } else {
      header.style.background = '#F6F5F0';
      header.style.backdropFilter = 'none';
    }
  }
});