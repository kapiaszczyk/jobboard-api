document.addEventListener('DOMContentLoaded', function () {
    const fetchButton = document.getElementById('fetchButton');
    fetchButton.addEventListener('click', fetchJobOffers);
});

const myHeaders = new Headers();
myHeaders.append("Authorization", "Basic YWRtaW46YWRtaW4=");
myHeaders.append("Content-Type", "application/json");
myHeaders.append("Access-Control-Allow-Origin", "http://localhost");

const requestOptions = {
  method: "GET",
  headers: myHeaders,
  redirect: "follow"
};

async function fetchJobOffers() {
    try {
        const response = await fetch("http://localhost:8080/api/v1/job-offers/detailed", requestOptions);      
        const jobOffers = await response.json();
        renderJobOffers(jobOffers);
    } catch (error) {
        console.error('Error fetching job offers:', error);
    }
}

function renderJobOffers(jobOffers) {

    const jobOffersContainer = document.getElementById('jobOffers');
    jobOffersContainer.innerHTML = '';

    jobOffers.forEach(offer => {
        const card = document.createElement('div');
        card.classList.add('bg-white', 'shadow-md', 'rounded', 'p-6', 'mb-4');

        const src = decodeBase64ToImageSrc(offer.company.logo);

        card.innerHTML = `
        <img src="${src}" alt="Company Logo" style="float: right;">
        <h2 class="text-xl font-bold mb-2">${offer.name}</h2>
        <p class="text-gray-700 mb-2"><span class="font-bold">${offer.company.name}</span> - ${offer.address.city}, ${offer.address.country}</p>
        <p class="text-gray-700 mb-2"><span class="font-bold">Contract type:</span> - ${replaceContractTypeFromEnum(offer.contractType)}</p>
        <p class="text-gray-700 mb-2"><span class="font-bold">Operating mode:</span> - ${replaceToSentenceCase(offer.operatingMode)}</p>
        <p class="text-gray-700 mb-2"><span class="font-bold">Salary:</span> ${offer.salary} ${offer.salaryCurrency}</p>
        <p class="text-gray-700 mb-2"><span class="font-bold">Experience:</span> ${replaceToSentenceCase(offer.experience)}</p>
        <p class="text-gray-700 mb-2"><span class="font-bold">Expires at:</span> ${new Date(offer.expiresAt).toLocaleString()}</p>
        <div class="flex flex-wrap">
            ${offer.technologies.map(tech => `<span class="bg-gray-200 text-gray-800 px-2 py-1 rounded-full text-xs mr-2 mb-2">${tech.technology.name} - ${replaceToSentenceCase(tech.degreeOfKnowledge)}</span>`).join('')}
        </div>
        `;

        jobOffersContainer.appendChild(card);
    });
}

function decodeBase64ToImageSrc(base64String) {
    src = `data:image/png;base64,${base64String}`;
    return src;
}

function replaceToSentenceCase(str) {
    // Replaces the first letter of the string to uppercase
    // and the rest of the string to lowercase
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
}

function replaceContractTypeFromEnum(str) {
    // Replace the _ character with a space
    str = str.replace(/_/g, ' ');

    // if str is B2B, then do not change to sentence case
    if (str === 'B2B') {
        return str;
    }
    return replaceToSentenceCase(str);
}