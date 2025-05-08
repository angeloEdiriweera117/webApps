function generatePassword() {
    const length = document.getElementById("length").value;
    const useUpper = document.getElementById("uppercase").checked;
    const useLower = document.getElementById("lowercase").checked;
    const useNumbers = document.getElementById("numbers").checked;
    const useSymbols = document.getElementById("symbols").checked;
  
    const upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    const lowerChars = "abcdefghijklmnopqrstuvwxyz";
    const numberChars = "0123456789";
    const symbolChars = "!@#$%^&*()_+[]{}|;:,.<>?";
    
    let charSet = "";
    if (useUpper) charSet += upperChars;
    if (useLower) charSet += lowerChars;
    if (useNumbers) charSet += numberChars;
    if (useSymbols) charSet += symbolChars;
    
    if (charSet.length === 0) {
      alert("Please select at least one character type.");
      return;
    }
  
    let password = "";
    const cryptoObj = window.crypto || window.msCrypto;
    const randomValues = new Uint32Array(length);
    cryptoObj.getRandomValues(randomValues);
    
    for (let i = 0; i < length; i++) {
      const index = randomValues[i] % charSet.length;
      password += charSet[index];
    }
  
    document.getElementById("output").innerText = password;
}

if ('serviceWorker' in navigator) {
    navigator.serviceWorker.register('/service-worker.js')
      .then(() => console.log("Service Worker Registered"));
}
  
  