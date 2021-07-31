import pickle
from os.path import dirname,join

# function to run for prediction
def detecting_fake_news(var):

    # retriving the best model for prediction call
    filename = join(dirname(__file__), "final_model.sav")
    fo = open(filename,'rb')
    load_model = pickle.load(fo)
    prediction = load_model.predict([var])
    
    return prediction[0]
