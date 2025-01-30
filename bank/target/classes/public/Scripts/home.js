const isLeapYear = (year) => {
    return (
        (year % 4 === 0 && year % 100 !== 0 && year % 400 !== 0) ||
        (year % 100 === 0 && year % 400 === 0)
    );
};
const getFebDays = (year) => {
    return isLeapYear(year) ? 29 : 28;
};
let calendar = document.querySelector(".calendar");
const month_names = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
];
let month_picker = document.querySelector("#month-picker");
const dayTextFormate = document.querySelector(".day-text-formate");
const timeFormate = document.querySelector(".time-formate");
const dateFormate = document.querySelector(".date-formate");

month_picker.onclick = () => {
    month_list.classList.remove("hideonce");
    month_list.classList.remove("hide");
    month_list.classList.add("show");
    dayTextFormate.classList.remove("showtime");
    dayTextFormate.classList.add("hidetime");
    timeFormate.classList.remove("showtime");
    timeFormate.classList.add("hideTime");
    dateFormate.classList.remove("showtime");
    dateFormate.classList.add("hideTime");
};

const generateCalendar = (month, year) => {
    let calendar_days = document.querySelector(".calendar-days");
    calendar_days.innerHTML = "";
    let calendar_header_year = document.querySelector("#year");
    let days_of_month = [
        31,
        getFebDays(year),
        31,
        30,
        31,
        30,
        31,
        31,
        30,
        31,
        30,
        31,
    ];

    let currentDate = new Date();

    month_picker.innerHTML = month_names[month];

    calendar_header_year.innerHTML = year;

    let first_day = new Date(year, month);

    for (let i = 0; i <= days_of_month[month] + first_day.getDay() - 1; i++) {
        let day = document.createElement("div");

        if (i >= first_day.getDay()) {
            day.innerHTML = i - first_day.getDay() + 1;

            if (
                i - first_day.getDay() + 1 === currentDate.getDate() &&
                year === currentDate.getFullYear() &&
                month === currentDate.getMonth()
            ) {
                day.classList.add("current-date");
            }
        }
        calendar_days.appendChild(day);
    }
};

let month_list = calendar.querySelector(".month-list");
month_names.forEach((e, index) => {
    let month = document.createElement("div");
    month.innerHTML = `<div>${e}</div>`;

    month_list.append(month);
    month.onclick = () => {
        currentMonth.value = index;
        generateCalendar(currentMonth.value, currentYear.value);
        month_list.classList.replace("show", "hide");
        dayTextFormate.classList.remove("hideTime");
        dayTextFormate.classList.add("showtime");
        timeFormate.classList.remove("hideTime");
        timeFormate.classList.add("showtime");
        dateFormate.classList.remove("hideTime");
        dateFormate.classList.add("showtime");
    };
});

(function () {
    month_list.classList.add("hideonce");
})();
document.querySelector("#pre-year").onclick = () => {
    --currentYear.value;
    generateCalendar(currentMonth.value, currentYear.value);
};
document.querySelector("#next-year").onclick = () => {
    ++currentYear.value;
    generateCalendar(currentMonth.value, currentYear.value);
};

let currentDate = new Date();
let currentMonth = { value: currentDate.getMonth() };
let currentYear = { value: currentDate.getFullYear() };
generateCalendar(currentMonth.value, currentYear.value);

const todayShowTime = document.querySelector(".time-formate");
const todayShowDate = document.querySelector(".date-formate");

const currshowDate = new Date();
const showCurrentDateOption = {
    year: "numeric",
    month: "long",
    day: "numeric",
    weekday: "long",
};
const currentDateFormate = new Intl.DateTimeFormat(
    "en-US",
    showCurrentDateOption
).format(currshowDate);
todayShowDate.textContent = currentDateFormate;
setInterval(() => {
    const timer = new Date();
    const option = {
        hour: "numeric",
        minute: "numeric",
        second: "numeric",
    };
    const formateTimer = new Intl.DateTimeFormat("pt-br", option).format(timer);
    let time = `${`${timer.getHours()}`.padStart(
        2,
        "0"
    )}:${`${timer.getMinutes()}`.padStart(
        2,
        "0"
    )}: ${`${timer.getSeconds()}`.padStart(2, "0")}`;
    todayShowTime.textContent = formateTimer;
}, 1000);

function openModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.style.display = "block";
    modal.style.opacity = 0;
    let opacity = 0;
    const fadeIn = setInterval(() => {
        if (opacity >= 1) {
            clearInterval(fadeIn);
        }
        modal.style.opacity = opacity;
        opacity += 0.1;
    }, 30);
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    let opacity = 1;
    const fadeOut = setInterval(() => {
        if (opacity <= 0) {
            clearInterval(fadeOut);
            modal.style.display = "none";
        }
        modal.style.opacity = opacity;
        opacity -= 0.1;
    }, 30);
}

window.onclick = function (event) {
    const modals = document.getElementsByClassName("modal");
    for (let i = 0; i < modals.length; i++) {
        if (event.target == modals[i]) {
            modals[i].style.display = "none";
        }
    }
};

document.addEventListener("DOMContentLoaded", function () {
    fetch("/getTransfersRecent")
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            const transfersByDate = data.reduce((acc, transfer) => {
                const date = new Date(transfer.data).toLocaleDateString(
                    "en-US"
                );
                if (!acc[date]) {
                    acc[date] = 0;
                }
                acc[date] += transfer.valor;
                return acc;
            }, {});

            const labels = Object.keys(transfersByDate);
            const values = Object.values(transfersByDate);

            const ctx = document.getElementById("myChart").getContext("2d");
            const myChart = new Chart(ctx, {
                type: "line",
                data: {
                    labels: labels,
                    datasets: [
                        {
                            data: values,
                            borderColor: "#023e8a",
                            backgroundColor: "rgba(2, 62, 138, 0.2)",
                            fill: true,
                            tension: 0.4,
                        },
                    ],
                },
                options: {
                    plugins: {
                        legend: {
                            display: false,
                            labels: {
                                color: "#03045e",
                            },
                        },
                        title: {
                            display: true,
                            text: "Your Transfers",
                            color: "#023e8a",
                            font: {
                                size: 18,
                                fontweight: "bold",
                            },
                        },
                    },
                    animations: {
                        tension: {
                            duration: 1000,
                            easing: "easeInOutQuad",
                            from: 0.4,
                            to: 0.2,
                            loop: true,
                        },
                    },
                    scales: {
                        x: {
                            ticks: {
                                color: "#023e8a",
                            },
                        },
                        y: {
                            beginAtZero: true,
                            ticks: {
                                color: "#023e8a",
                            },
                        },
                    },
                },
            });
        })
        .catch((error) => console.error("Error fetching data:", error));
});
