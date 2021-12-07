/*
    check login status
*/
window.addEventListener('load', async () => {

    console.log('index.checkLoginStatus executed');

    let res = await fetch('http://127.0.0.1:8080/checkloginstatus', {
        method: 'GET',
        credentials: 'include' 
    });

    // 200 code = already logged in
    if (res.status === 200) {
        let userObj = await res.json();

        if (userObj.userRole === 'finance manager') {
            window.location.href = '/HTML/financeManagerHomepage.html';
        } 
    } else if (res.status === 401) {
        window.location.href = '/index.html';
    }

    // retrieve all reimbursement table once sign in
    populateReimbursementTable();
});

/*
    logout btn
*/
let logoutBtn = document.querySelector('#logout-btn');

logoutBtn.addEventListener('click', async () => {
    let res = await fetch('http://127.0.0.1:8080/logout', {
        'method' : 'POST',
        'credentials' : 'include'
    });

    if (res.status === 200) {
        window.location.href = '/index.html';
    }
})

/*
    populate table
*/
async function populateReimbursementTable() {

    let res = await fetch('http://127.0.0.1:8080/reimbursements', {
        credentials: 'include',
        method: 'GET'
    });

    // manage table
    let tbodyElement = document.querySelector("#reimbursements-table tbody");
    console.log(tbodyElement);

    tbodyElement.innerHTML = ''; // clear data in the tbody
    let reimbursementArray = await res.json();

    for (let i = 0; i < reimbursementArray.length; i++) {
        let reimbursement = reimbursementArray[i];

        let tr = document.createElement('tr');

        let tdReimbId = document.createElement('td');
        tdReimbId.innerHTML = reimbursement.reimbId;
    
        let tdAmount = document.createElement('td');
        tdAmount.innerHTML = reimbursement.amount;
    
        let tdType = document.createElement('td');
        tdType.innerHTML = reimbursement.type;

        let tdDesc = document.createElement('td');
        tdDesc.innerHTML = reimbursement.description;

        let tdReceipt = document.createElement('td');
        let viewImageBtn = document.createElement('button');
        viewImageBtn.innerHTML = 'View receipt';
        tdReceipt.appendChild(viewImageBtn);

        viewImageBtn.addEventListener('click', () => {
            /* 
                add the is-active class to the modal (so that the modal appears)
                inside of the modal on div.modal-content (div w/ class modal-content)
                -> img tag with src="http://127.0.0.1:8080/reimbursements/3/receipt"
            */
            let receiptImageModal = document.querySelector('#receipt-image-modal');

            //close button
            let modalCloseElement = document.querySelector('button');
        //    modalCloseElement.setAttribute('class', 'modal-close');
        //    modalCloseElement.setAttribute('aria-label', 'close');
            modalCloseElement.addEventListener('click', () => {
                receiptImageModal.classList.remove('is-active');

                console.log('classList.remove');
            });
        //    receiptImageModal.appendChild(modalCloseElement);

            // use querySelector on the element to find the child elements nested within
            let modalContentElement = receiptImageModal.querySelector('.modal-content');
            modalContentElement.innerHTML = ''; // clear data

            let imageElement = document.createElement('img');
            imageElement.setAttribute('src', `http://127.0.0.1:8080/reimbursements/${reimbursement.reimbId}/receipt`);
            imageElement.setAttribute('alt', 'Receipt image not found.');
            modalContentElement.appendChild(imageElement);

            // add a class to the modal element to have it display
            receiptImageModal.classList.add('is-active');
        });

        let tdStat = document.createElement('td');
        tdStat.innerHTML = reimbursement.status;

        let tdSubTime = document.createElement('td');
        tdSubTime.innerHTML = reimbursement.submittedTime;

        let tdResTime = document.createElement('td');
        tdResTime.innerHTML = reimbursement.resolvedTime;

        let tdAuthId = document.createElement('td');
        tdAuthId.innerHTML = reimbursement.authorId;

        let tdResId = document.createElement('td');
        tdResId.innerHTML = reimbursement.resolverId;

        tr.appendChild(tdReimbId);
        tr.appendChild(tdAmount);
        tr.appendChild(tdType);
        tr.appendChild(tdDesc);
        tr.appendChild(tdReceipt);
        tr.appendChild(tdStat);
        tr.appendChild(tdSubTime);
        tr.appendChild(tdResTime);
        tr.appendChild(tdAuthId);
        tr.appendChild(tdResId);

        tbodyElement.appendChild(tr);
    }   
}

/*
    submit new reimbursement
*/
let reimbursementSubmitBtn = document.querySelector('#submit-reimbursement-btn');

reimbursementSubmitBtn.addEventListener('click', submitReimbursement);

async function submitReimbursement() {

    let amount = document.querySelector('#amount');
    let type = document.querySelector('#type-dropdown');
    let description = document.querySelector('#description');
    let receipt = document.querySelector('#receipt-file');

    const file = receipt.files[0];

    let formData = new FormData();
    formData.append('amount', amount.value);
    formData.append('type', type.value);
    formData.append('description', description.value);
    formData.append('receipt', file);

    let res = await fetch('http://127.0.0.1:8080/reimbursements', {
        method: 'POST',
        credentials: 'include',
        body: formData
    });

    if (res.status === 201) { // if successfully added reimbursement =>
        populateReimbursementTable();; // refresh the reimbursement table

    } else if (res.status === 400) { // if status code 400 =>
        let reimbForm = document.querySelector('.errorMessage');

        let data = await res.json();
        
        // remove old pTag
        let oldPtag = document.querySelector('#ptag')
        if (oldPtag != null){
            oldPtag.remove();
        }

        let pTag = document.createElement('p');
        pTag.setAttribute('id','ptag')
        pTag.innerHTML = data.message; // return data message
        pTag.style.color = 'red';

        reimbForm.appendChild(pTag);
    } 
}
