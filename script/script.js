window.addEventListener('scroll', () => {
    const nav = document.querySelector('.navbar');
    if (nav) nav.classList.toggle('scrolled', window.scrollY > 5);
});

const projectData = {
    'lucky9': ['assets/images/one.jpg', 'assets/images/two.jpg', 'assets/images/three.jpg'],
    'guessing': ['assets/images/guess.jpg']
};

const scrapbookScreenshots = [
    "assets/images/1.jpg", "assets/images/2.jpg", "assets/images/3.jpg", "assets/images/4.jpg",
    "assets/images/5.jpg", "assets/images/6.jpg", "assets/images/7.jpg", "assets/images/8.jpg",
    "assets/images/6.jpg", "assets/images/9.jpg"
];

let currentImages = [];
let currentIdx = 0;

function openOverlay(projectId) {
    const overlay = document.getElementById('image-overlay');
    currentImages = (projectId === 'scrapbook') ? scrapbookScreenshots : (projectData[projectId] || []);
    if (currentImages.length === 0) return;
    currentIdx = 0;
    if (overlay) {
        overlay.style.display = 'flex';
        setTimeout(() => overlay.classList.add('active'), 10);
        updateView();
    }
}

function updateView() {
    const overlayImg = document.getElementById('main-overlay-img');
    const counter = document.getElementById('img-counter');
    if (!overlayImg || currentImages.length === 0) return;
    overlayImg.style.opacity = '0';
    setTimeout(() => {
        overlayImg.src = currentImages[currentIdx];
        if (counter) counter.innerText = `${currentIdx + 1} / ${currentImages.length}`;
        overlayImg.style.opacity = '1';
    }, 150);
}

window.nextImg = function() {
    if (currentImages.length <= 1) return;
    currentIdx = (currentIdx + 1) % currentImages.length;
    updateView();
};

window.prevImg = function() {
    if (currentImages.length <= 1) return;
    currentIdx = (currentIdx - 1 + currentImages.length) % currentImages.length;
    updateView();
};

window.closeOverlay = function() {
    const overlay = document.getElementById('image-overlay');
    if (overlay) {
        overlay.classList.remove('active');
        setTimeout(() => { overlay.style.display = 'none'; }, 300);
    }
};

window.closeContact = function() {
    const contactOverlay = document.getElementById('contact-overlay');
    if (contactOverlay) {
        contactOverlay.classList.remove('active');
        setTimeout(() => { contactOverlay.style.display = 'none'; }, 300);
    }
};

window.openContact = function() {
    const contactOverlay = document.getElementById('contact-overlay');
    if (contactOverlay) {
        contactOverlay.style.display = 'flex';
        setTimeout(() => contactOverlay.classList.add('active'), 10);
    }
};

document.addEventListener('DOMContentLoaded', () => {
    if (typeof emailjs !== 'undefined') emailjs.init("x9ALqxHUsl_RHE5pj");

    document.querySelectorAll('.trigger-gallery').forEach(btn => {
        btn.onclick = () => {
            const id = btn.getAttribute('data-project') || 'scrapbook';
            openOverlay(id);
        };
    });

    const nextBtn = document.getElementById('nextBtn');
    const prevBtn = document.getElementById('prevBtn');
    if (nextBtn) nextBtn.onclick = (e) => { e.stopPropagation(); nextImg(); };
    if (prevBtn) prevBtn.onclick = (e) => { e.stopPropagation(); prevImg(); };

    const contactForm = document.getElementById('contact-form');
    if (contactForm) {
        contactForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const btn = document.getElementById('send-btn');
            const msg = document.getElementById('status-msg');
            if (btn) { btn.innerText = 'Sending...'; btn.disabled = true; }

            emailjs.sendForm('service_lppnzoj', 'template_8cwoa1h', this)
                .then(() => {
                    if (btn) btn.innerText = 'Sent Successfully!';
                    if (msg) {
                        msg.style.color = '#4CAF50';
                        msg.innerText = 'Thank you! Your message has been sent.';
                    }
                    setTimeout(() => {
                        closeContact();
                        this.reset();
                        if (btn) { btn.innerText = 'Send Message'; btn.disabled = false; }
                        if (msg) msg.innerText = '';
                    }, 2500);
                }, (err) => {
                    if (btn) { btn.disabled = false; btn.innerText = 'Send Message'; }
                    if (msg) {
                        msg.style.color = '#ff4d4d';
                        msg.innerText = 'Error sending message.';
                    }
                    console.log("Error:", err);
                });
        });
    }
});

function downloadCV() {
    const fileUrl = 'assets/cv.pdf';
    const fileName = 'Shaina_Lucena_CV.pdf';
    
    fetch(fileUrl)
        .then(response => response.blob())
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = fileName;
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
            a.remove();
        })
        .catch(() => {
            // Backup sakaling magka-error ang fetch (bubuksan sa bagong tab)
            window.open(fileUrl, '_blank');
        });
}