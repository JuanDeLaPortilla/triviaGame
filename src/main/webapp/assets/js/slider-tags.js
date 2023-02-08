let swiper2 = new Swiper('.tags', {
    slidesPerView: 8,
    pagination: {
        el: ".swiper-pagination",
        clickable: true,
    },
    breakpoints: {
        960: {
            slidesPerView: 5,
        },
        720: {
            slidesPerView: 4,
        },
        540: {
            slidesPerView: 3,
        },
        320: {
            slidesPerView: 2,
        },
        240:{
            slidesPerView: 1,
        }
    }
});
